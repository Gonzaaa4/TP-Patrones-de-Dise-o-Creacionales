# Requerimiento 1: El Motor de Renderizado

Analizando el Requerimiento 1, nos encontramos con un problema clásico de instanciación, el sistema necesita crear objetos (`RenderizadorPDF`, `RenderizadorExcel`, etc.) que comparten una funcionalidad común (renderizar), pero cuya implementación es muy diferente.

Los puntos clave que identificamos son:

**1. Desacoplamiento:** El cliente (ej. `ModuloFinanzas`) no debe conocer las clases concretas.
No puede haber un `new RenderizadorPDF()` en el código del cliente. 
**2. Interfaz Simple:** El cliente solo quiere "pedir" un renderizador basado en un identificador, como un string: "PDF".
**3. Extensibilidad:** El sistema debe ser fácil de extender (ej. agregar `ReporteXML`) sin modificar el código cliente, este punto es una referencia directa al Principio Abierto/Cerrado (OCP).

El problema de "crear un objeto de entre varios tipos posibles sin que el cliente sepa cómo se crea" es el territorio del patrón Factory. 

De las opciones dadas (Factory Method o Abstract Factory), el patrón Abstract Factory no aplica aquí, este patrón se usa para crear familias de objetos relacionados (ej. un `UIFactory` que crea `Boton`, `Checkbox` y `TextField` para Windows o para MacOS), nosotros solo necesitamos crear un objeto: el `IRenderizadorReporte`.

Por lo tanto, el patrón idóneo es el Factory Method, en la práctica la solución más limpia y directa para este escenario es implementar una clase Factory que encapsula la lógica de decisión, esta clase proporciona un método que actúa como la "fábrica" de los objetos renderizadores.

**Patrón Elegido:** Patrón Factory (implementado a través de una clase `RenderizadorFactory` con un método estático). 

---

#### **¿Qué patrón de diseño creacional eligieron?** 

Elegimos el patrón Factory. Nuestra implementación consiste en crear una interfaz común `IRenderizadorReporte` para todos los renderizadores y una clase `RenderizadorFactory` dedicada, que contiene un método estático `crearRenderizador(String tipo)`.

---

#### **¿Por qué este patrón es la solución adecuada para este problema?**

Es la solución ideal porque centraliza la lógica de creación de objetos en un solo punto.
- El cliente (`ModuloFinanzas`) ya no tiene la responsabilidad de saber qué clase concreta instanciar. 
- Cumple perfectamente con el requisito de que el cliente solo "pida" lo que necesita (ej. "PDF") y reciba el objeto adecuado.
- El cliente solo interactúa con la fábrica y con la interfaz `IRenderizadorReporte`, logrando un bajo acoplamiento.

---

#### **¿Qué problema(s) evita (ej. acoplamiento, violación del principio Abierto/Cerrado)?**

Este patrón ataca directamente los problemas de diseño planteados:
**1. Evita el Alto Acoplamiento:** Es el problema principal que soluciona, el código cliente ya no depende de las implementaciones concretas (`RenderizadorPDF`, `RenderizadorCSV`), su única dependencia es la interfaz `IRenderizadorReporte` y la `RenderizadorFactory`, si mañana la clase `RenderizadorPDF` cambia de nombre o su constructor requiere un nuevo parámetro, el código cliente no se ve afectado en absoluto.

**2. Evita la Violación del Principio Abierto/Cerrado (OCP):** Este es el punto más fuerte, el requisito pide explícitamente poder agregar nuevos formatos (como `ReporteXML`) sin modificar el código cliente existente.
- **Sin el patrón:** El cliente tendría un `switch` o un `if-else` para instanciar el renderizador, para agregar XML habría que modificar ese `switch` en todos los lugares donde se use (ej. `ModuloFinanzas`, `ModuloMarketing`, etc.), esto es una clara violación del OCP. 
- **Con el patrón:** El `switch` vive dentro de la `RenderizadorFactory`, para agregar XML creamos la clase `RenderizadorXML` y añadimos un `case "XML":` únicamente dentro de la fábrica, el `ModuloFinanzas` y el resto de los clientes no se modifican en absoluto, el código cliente está cerrado a la modificación pero el sistema está abierto a la extensión.










# Requerimiento 2: La Construcción de los Reportes

Analizando el Requerimiento 2 , el problema es claro, necesitamos instanciar un objeto Reporte que es una "entidad compleja", esta complejidad no viene de su lógica interna, sino de su construcción.

Identificamos los siguientes puntos clave:
**1. Parámetros Mixtos:** El objeto tiene una combinación de datos obligatorios (título, cuerpo) y un conjunto de datos opcionales (encabezado, pie, fecha, autor, orientación) .
**2. Legibilidad:** La consigna exige que el método de creación sea "limpio y legible".
**3. Anti-Patrones Prohibidos:** Se prohíbe explícitamente usar un "constructor telescópico" o un constructor con 7 parámetros donde se pasen nulls.

El constructor telescópico (tener Reporte(String t, String c), Reporte(String t, String c, String e), Reporte(String t, String c, String e, String p)...) es una pesadilla de mantenimiento.

El constructor gigante (Reporte("Mi Título", "Datos...", null, "Mi Pie", null, "Yo", Orientacion.VERTICAL)) es ilegible y muy propenso a errores (¿qué pasa si invierto el orden de dos nulls?).

El problema de "cómo construir un objeto complejo con muchos parámetros opcionales de forma legible y segura" es el caso de estudio perfecto para el Patrón Builder.

**Patrón Elegido:** Patrón Builder.

---

#### **¿Qué patrón de diseño creacional eligieron?**
Elegimos el patrón Builder. Este patrón consiste en crear un objeto "ayudante" (el Builder) que se encarga de recibir los parámetros de configuración paso a paso. Una vez que el Builder tiene toda la información, se le pide que "construya" (build()) el objeto final (Reporte) de una sola vez.

---

#### **¿Por qué este patrón es la solución adecuada?**
Es la solución ideal porque separa el proceso de construcción del objeto de su representación final.
- **Maneja la Opcionalidad:** Permite al cliente especificar solo los parámetros opcionales que necesita, sin tener que pasar null para los que no usa.
- **Impone la Obligatoriedad:** Podemos forzar al cliente a proveer los datos obligatorios (título, cuerpo)  a través del constructor del propio Builder, garantizando que el objeto final siempre será válido.
- **Mejora la Legibilidad:** El código cliente se vuelve auto-descriptivo, en lugar de una lista de nulls tenemos una cadena de métodos con nombres claros: .setAutor("..."), .setPieDePagina("...").

---

#### **¿Qué problemas específicos del "constructor" resuelve?**
Resuelve directamente los dos anti-patrones mencionados en la consigna:
**1. Resuelve el "Constructor Telescópico":** No necesitamos crear N constructores para las N combinaciones de parámetros opcionales, solo tenemos un constructor en el Reporte (que es privado) y un constructor en el Builder (que pide solo lo obligatorio).
**2. Resuelve el "Constructor con 7 parámetros":** Evita el "Mega-Constructor", el cliente no tiene que memorizar el orden de 7 parámetros, simplemente llama a los métodos "setter" del builder por su nombre, en el orden que prefiera, esto elimina los errores de pasar un String en el parámetro incorrecto.

Además, el patrón Builder facilita la inmutabilidad del objeto Reporte, el Reporte final puede tener todos sus atributos final, ya que se asignan una sola vez dentro de su constructor (que solo el Builder llama).





# Requerimiento 3: El Gestor de Configuración Global

El Requerimiento 3 es muy directo en sus especificaciones, el sistema necesita un objeto (GestorConfiguracion) que centralice datos de configuración global, como las credenciales de la base de datos y las rutas de salida .

Los puntos clave son restricciones absolutas:
**1. Punto de Acceso Único:** Toda la aplicación debe usar el mismo punto de acceso para obtener esta configuración.
**2. Unicidad de Instancia:** La consigna es explícita: "Es fundamental garantizar que solo exista una y solo una instancia".
**3. Justificación:** Se busca evitar la ineficiencia y más importante las "inconsistencias en los datos"  que ocurrirían si existieran múltiples objetos de configuración con datos diferentes.

Este escenario donde se debe restringir la instanciación de una clase a un único objeto y proporcionar un acceso global a él, es la definición de libro de texto del patrón Singleton.

**Patrón Elegido:** Patrón Singleton.

---

#### **¿Qué patrón de diseño creacional eligieron?**
Elegimos el patrón Singleton, este patrón pertenece al grupo de los creacionales porque, aunque su foco principal es el control de instancias, gestiona la forma en que el objeto es creado (o, mejor dicho, cómo se evita su creación múltiple).

---

#### **¿Por qué este patrón es la solución adecuada para este requerimiento?**
Es la única solución que cumple directamente con la restricción más fuerte del requerimiento: "garantizar que solo exista una y solo una instancia".

El GestorConfiguracion es un ejemplo perfecto de un recurso que es, por naturaleza, singular en la aplicación. No tiene sentido tener dos configuraciones de base de datos activas al mismo tiempo. El patrón Singleton nos da un control centralizado sobre ese recurso compartido, evitando que diferentes módulos (Finanzas, Marketing, etc.) carguen accidentalmente copias diferentes de la configuración, lo que llevaría a inconsistencias.

---

#### **¿Cómo garantizaron la unicidad de la instancia?**
Para garantizar la unicidad de la instancia, la implementación del Singleton se basa en tres pilares técnicos:
**1. Constructor Privado:** Declaramos el constructor de GestorConfiguracion como private. Esto es crucial, ya que impide que cualquier otra clase pueda crear una instancia usando new GestorConfiguracion().
**2. Instancia Estática Privada:** Creamos un atributo private static final dentro de la propia clase. Este atributo contendrá la única instancia que existirá.
**3. Método de Acceso Estático Público:** Creamos un método public static (comúnmente llamado getInstance()) que actúa como el punto de acceso global. Este método simplemente devuelve la instancia única que la clase guarda internamente.

En nuestra implementación usamos la inicialización temprana (Eager Initialization), esto significa que la instancia se crea en el momento en que la clase es cargada por la JVM, lo cual es la forma más simple y segura de implementar un Singleton en un entorno multi-hilo (como lo sería una aplicación real con múltiples módulos).
