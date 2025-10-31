classDiagram
    direction LR

    %% --- 1. Requerimiento 1: Patrón Factory ---
    class IRenderizadorReporte {
        <<interface>>
        +renderizar(Reporte reporte): void
    }
    note for IRenderizadorReporte "Nota: El PDF indica (datos: String) pero (Reporte reporte) es más coherente con el Builder, que genera un objeto Reporte."

    class RenderizadorPDF {
        +renderizar(Reporte reporte): void
    }
    class RenderizadorExcel {
        +renderizar(Reporte reporte): void
    }
    class RenderizadorCSV {
        +renderizar(Reporte reporte): void
    }

    RenderizadorPDF --|> IRenderizadorReporte
    RenderizadorExcel --|> IRenderizadorReporte
    RenderizadorCSV --|> IRenderizadorReporte

    class RenderizadorFactory {
        <<Factory>>
        +static crearRenderizador(String tipo): IRenderizadorReporte
    }
    
    RenderizadorFactory ..> IRenderizadorReporte : "crea"
    RenderizadorFactory ..> RenderizadorPDF : "instancia"
    RenderizadorFactory ..> RenderizadorExcel : "instancia"
    RenderizadorFactory ..> RenderizadorCSV : "instancia"


    %% --- 2. Requerimiento 2: Patrón Builder ---
    
    
    %% Se usa un ID 'ReporteBuilder' para la clase que se mostrará como 'Reporte.Builder'
    class ReporteBuilder["Reporte.Builder"] {
        <<Builder>>
        -titulo: String
        -cuerpo: String
        -encabezado: String
        -pieDePagina: String
        -fecha: LocalDate
        -autor: String
        -orientacion: Orientacion
        +Reporte.Builder(String titulo, String cuerpo)
        +setEncabezado(String): ReporteBuilder
        +setPieDePagina(String): ReporteBuilder
        +setFecha(LocalDate): ReporteBuilder
        +setAutor(String): ReporteBuilder
        +setOrientacion(Orientacion): ReporteBuilder
        +build(): Reporte
    }

    class Reporte {
        <<Product>>
        -titulo: String
        -cuerpo: String
        -encabezado: String
        -pieDePagina: String
        -fecha: LocalDate
        -autor: String
        -orientacion: Orientacion
        %% Se usa el ID 'ReporteBuilder' como tipo de parámetro
        -Reporte(ReporteBuilder builder)
    }

    class Orientacion {
        <<enumeration>>
        VERTICAL
        HORIZONTAL
    }

    %% Se usa el ID 'ReporteBuilder' para la relación
    ReporteBuilder ..> Reporte : "construye"
    Reporte --o "1" Orientacion : "tiene una"


    %% --- 3. Requerimiento 3: Patrón Singleton ---
    class GestorConfiguracion {
        <<Singleton>>
        -static final instancia: GestorConfiguracion
        -urlBd: String
        -userBd: String
        -pathReportes: String
        -GestorConfiguracion()
        +static getInstance(): GestorConfiguracion
        +getUrlBd(): String
        +setPathReportes(String path): void
    }


    %% --- 4. Cliente (Demostración) ---
    class Main {
        <<Client>>
        +static main(String[] args): void
        demostrarFactory(): void
        demostrarBuilder(): void
        demostrarSingleton(): void
    }

    Main ..> RenderizadorFactory : "usa"
    %% Se usa el ID 'ReporteBuilder' para la relación
    Main ..> ReporteBuilder : "usa"
    Main ..> GestorConfiguracion : "usa"
    Main ..> IRenderizadorReporte : "usa"
    IRenderizadorReporte ..> Reporte : "renderiza un"
