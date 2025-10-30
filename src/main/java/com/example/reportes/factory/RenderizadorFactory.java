package com.example.reportes.factory;

/**
 * Requerimiento 1: El Motor de Renderizado.
 * Implementa el patrón Factory (El Creador).
 */
public class RenderizadorFactory {

    /**
     * El "Factory Method".
     * Recibe un tipo y devuelve la instancia correcta.
     */
    public static IRenderizadorReporte crearRenderizador(String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("El tipo de reporte no puede ser nulo o vacío.");
        }

        switch (tipo.toUpperCase()) {
            case "PDF":
                return new RenderizadorPDF();
            case "EXCEL":
                return new RenderizadorExcel();
            case "CSV":
                return new RenderizadorCSV();
            default:
                throw new IllegalArgumentException("Tipo de reporte no soportado: " + tipo);
        }
    }
}
