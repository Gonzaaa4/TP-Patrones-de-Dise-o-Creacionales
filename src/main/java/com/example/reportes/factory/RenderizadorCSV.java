package com.example.reportes.factory;

// Implementación concreta para CSV
public class RenderizadorCSV implements IRenderizadorReporte {
    @Override
    public void renderizar(String datos) {
        // Lógica compleja de renderizado de CSV...
        System.out.println("✔️ Renderizando [CSV]: " + datos);
    }
}
