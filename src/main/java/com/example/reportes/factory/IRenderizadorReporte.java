package com.example.reportes.factory;

/**
 * Interfaz común (Producto Abstracto) para todos los renderizadores.
 */
public interface IRenderizadorReporte {
    /**
     * Renderiza los datos del reporte en un formato específico.
     * @param datos Los datos (ej. un objeto Reporte) a renderizar.
     */
    void renderizar(String datos);
}
