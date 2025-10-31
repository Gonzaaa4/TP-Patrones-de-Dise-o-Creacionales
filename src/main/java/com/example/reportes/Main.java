package com.example.reportes;


import com.example.reportes.builder.Orientacion;
import com.example.reportes.builder.Reporte;
import com.example.reportes.factory.IRenderizadorReporte;
import com.example.reportes.factory.RenderizadorFactory;
import com.example.reportes.singleton.GestorConfiguracion;

import java.time.LocalDate;

/**
 * Clase principal que demuestra el uso de los tres patrones.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("==================================================");
        System.out.println("DEMOSTRACIÓN: PATRONES DE DISEÑO CREACIONALES");
        System.out.println("==================================================");

        // --- 1. Demostración del Patrón Singleton ---
        System.out.println("\n--- 1. Demostración Singleton (Req 3) ---");
        demostrarSingleton();

        // --- 2. Demostración del Patrón Builder ---
        System.out.println("\n--- 2. Demostración Builder (Req 2) ---");
        demostrarBuilder();

        // --- 3. Demostración del Patrón Factory ---
        System.out.println("\n--- 3. Demostración Factory (Req 1) ---");
        demostrarFactory();
    }

    private static void demostrarSingleton() {
        GestorConfiguracion configFinanzas = GestorConfiguracion.getInstance();
        System.out.println("Módulo Finanzas usa BD: " + configFinanzas.getUrlBd());

        GestorConfiguracion configMarketing = GestorConfiguracion.getInstance();
        System.out.println("Módulo Marketing usa Path: " + configMarketing.getPathReportes());

        if (configFinanzas == configMarketing) {
            System.out.println(">>> (Finanzas y Marketing tienen la MISMA instancia)");
        }

        configMarketing.setPathReportes("/var/www/reportes/NUEVO_PATH/");
        System.out.println("Módulo Finanzas (después del cambio) ve: " + configFinanzas.getPathReportes());
    }

    private static void demostrarBuilder() {
        // 1. Reporte simple
        Reporte reporteSimple = new Reporte.Builder(
                "Ventas Diarias", 
                "Contenido del cuerpo principal...")
                .build();

        System.out.println("Reporte Simple Creado:");
        System.out.println(reporteSimple);

        // 2. Reporte complejo
        Reporte reporteComplejo = new Reporte.Builder(
                "Análisis Financiero Anual", 
                "Cuerpo extenso del análisis...")
            .setEncabezado("Reporte Confidencial")
            .setPieDePagina("Página 1 de 10")
            .setAutor("Equipo de Finanzas")
            .setFecha(LocalDate.of(2025, 10, 30))
            .setOrientacion(Orientacion.HORIZONTAL)
            .build();

        System.out.println("\nReporte Complejo Creado:");
        System.out.println(reporteComplejo);
    }

    private static void demostrarFactory() {
        System.out.println("Módulo Finanzas pide un renderizador 'PDF'");
        IRenderizadorReporte motorPdf = RenderizadorFactory.crearRenderizador("PDF");
        motorPdf.renderizar("Datos de balance de Finanzas");

        System.out.println("Módulo RRHH pide un renderizador 'CSV'");
        IRenderizadorReporte motorCsv = RenderizadorFactory.crearRenderizador("CSV");
        motorCsv.renderizar("Listado de empleados de RRHH");

        System.out.println("Módulo Marketing pide un renderizador 'EXCEL'");
        IRenderizadorReporte motorExcel = RenderizadorFactory.crearRenderizador("EXCEL");
        motorExcel.renderizar("Datos de campaña de Marketing");

        try {
            RenderizadorFactory.crearRenderizador("XML");
        } catch (IllegalArgumentException e) {
            System.out.println("Intento de pedir 'XML' (aún no implementado): " + e.getMessage());
        }
    }
}
