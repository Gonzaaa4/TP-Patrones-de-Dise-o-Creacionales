package com.example.reportes.singleton;

/**
 * Requerimiento 3: Gestor de Configuración Global.
 * Implementa el patrón Singleton.
 */
public class GestorConfiguracion {

    private static final GestorConfiguracion instancia = new GestorConfiguracion();

    // Atributos de configuración
    private String urlBd;
    private String userBd;
    private String pathReportes;

    // 1. Constructor Privado.
    private GestorConfiguracion() {
        // Simulación de carga de datos
        this.urlBd = "jdbc:mysql://servidor.global:3306/datos_empresa";
        this.userBd = "usuario_prod";
        this.pathReportes = "/var/www/reportes/";

        System.out.println(">>> GestorConfiguracion inicializado (SINGLETON).");
    }

    // 3. Método de Acceso Estático Público.
    public static GestorConfiguracion getInstance() {
        return instancia;
    }

    // --- Getters y Setters ---

    public String getUrlBd() {
        return urlBd;
    }

    public String getUserBd() {
        return userBd;
    }

    public String getPathReportes() {
        return pathReportes;
    }

    public void setPathReportes(String pathReportes) {
        this.pathReportes = pathReportes;
    }
}
