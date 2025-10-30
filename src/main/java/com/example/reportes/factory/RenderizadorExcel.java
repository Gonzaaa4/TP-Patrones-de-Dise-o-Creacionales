package com.example.reportes.factory;

// Implementación concreta para Excel
public class RenderizadorExcel implements IRenderizadorReporte {
    @Override
    public void renderizar(String datos) {
        // Lógica compleja de renderizado de Excel...
        System.out.println("✔️ Renderizando [Excel]: " + datos);
    }
}
