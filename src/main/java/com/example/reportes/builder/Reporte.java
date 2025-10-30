package com.example.reportes.builder;

import java.time.LocalDate;

/**
 * Requerimiento 2: Construcción de Reportes.
 * Esta es la clase "Producto" que representa el objeto complejo.
 * Su constructor es privado; solo el Builder puede llamarlo.
 */
public class Reporte {

    // Datos ObligatorIOS
    private final String titulo;
    private final String cuerpoPrincipal;

    // Datos Opcionales
    private final String encabezado;
    private final String pieDePagina;
    private final LocalDate fecha;
    private final String autor;
    private final Orientacion orientacion;

    /**
     * Constructor privado. Solo el Builder puede crear instancias de Reporte.
     */
    private Reporte(Builder builder) {
        this.titulo = builder.titulo;
        this.cuerpoPrincipal = builder.cuerpoPrincipal;
        this.encabezado = builder.encabezado;
        this.pieDePagina = builder.pieDePagina;
        this.fecha = builder.fecha;
        this.autor = builder.autor;
        this.orientacion = builder.orientacion;
    }

    // Método para mostrar la configuración del reporte
    @Override
    public String toString() {
        return "Reporte [titulo=" + titulo + 
               ", cuerpoPrincipal (longitud)=" + (cuerpoPrincipal != null ? cuerpoPrincipal.length() : 0) + 
               ", encabezado=" + encabezado + 
               ", pieDePagina=" + pieDePagina + 
               ", fecha=" + fecha + 
               ", autor=" + autor + 
               ", orientacion=" + orientacion + "]";
    }

    // --- Getters (opcional, si se necesitan) ---
    public String getTitulo() { return titulo; }
    public String getCuerpoPrincipal() { return cuerpoPrincipal; }


    /**
     * Clase estática anidada (inner static class) que actúa como Builder.
     */
    public static class Builder {
        
        // Datos ObligatorIOS (final)
        private final String titulo;
        private final String cuerpoPrincipal;

        // Datos Opcionales (con valores por defecto)
        private String encabezado = "";
        private String pieDePagina = "";
        private LocalDate fecha = null;
        private String autor = "Sistema";
        private Orientacion orientacion = Orientacion.VERTICAL;

        /**
         * El constructor del Builder recibe solo los parámetros OBLIGATORIOS.
         */
        public Builder(String titulo, String cuerpoPrincipal) {
            if (titulo == null || cuerpoPrincipal == null) {
                throw new IllegalArgumentException("Título y Cuerpo son obligatorios");
            }
            this.titulo = titulo;
            this.cuerpoPrincipal = cuerpoPrincipal;
        }

        /**
         * Métodos "setter" para los parámetros opcionales.
         * Devuelven "this" para permitir el encadenamiento (fluent interface).
         */
        public Builder setEncabezado(String encabezado) {
            this.encabezado = encabezado;
            return this;
        }

        public Builder setPieDePagina(String pieDePagina) {
            this.pieDePagina = pieDePagina;
            return this;
        }

        public Builder setFecha(LocalDate fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder setAutor(String autor) {
            this.autor = autor;
            return this;
        }

        public Builder setOrientacion(Orientacion orientacion) {
            this.orientacion = orientacion;
            return this;
        }

        /**
         * Método final que construye y devuelve el objeto Reporte.
         */
        public Reporte build() {
            return new Reporte(this);
        }
    }
}
