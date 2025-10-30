package com.example.reportes.factory;

// Implementación concreta para PDF
public class RenderizadorPDF implements IRenderizadorReporte {
    @Override
    public void renderizar(String datos) {
        // Lógica compleja de renderizado de PDF...
        System.out.println("✔️ Renderizando [PDF]: " + datos);
    }
}
