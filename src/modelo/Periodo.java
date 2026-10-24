package modelo;

public class Periodo {

    private int mes;
    private int anio;

    // Constructor completo
    public Periodo(int mes, int anio) {
        // Validación pedagógica: Evita que se creen periodos con meses imposibles
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        this.mes = mes;
        this.anio = anio;
    }

    // Método de utilidad para comparar si dos periodos son el mismo de forma abreviada
    public boolean esIgualA(int mes, int anio) {
        return this.mes == mes && this.anio == anio;
    }

    // Sobreescritura para pintar de forma minimalista el periodo en JTables o ComboBoxes
    @Override
    public String toString() {
        return String.format("%02d/%d", this.mes, this.anio); // Retorna formatos estéticos como "05/2026"
    }

    // --- GETTERS Y SETTERS ---
    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}
