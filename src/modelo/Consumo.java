package modelo;

public abstract class Consumo {

    protected double valorConsumido;
    // Agrupamos el tiempo dentro del objeto compuesto Periodo
    protected Periodo periodo;
    protected String estadoSostenibilidad;

    // Arreglo constante para mapear los números a texto de forma centralizada en la visualización
    private static final String[] NOMBRES_MESES = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    // Constructor que recibe el objeto Periodo directamente
    public Consumo(double valorConsumido, Periodo periodo) {
        this.valorConsumido = valorConsumido;
        this.periodo = periodo;
        this.estadoSostenibilidad = "Pendiente";
    }

    public abstract void calcularEstadoSostenibilidad();

    public abstract String getTipoServicio();

    /**
     * Mapea los datos del objeto periodo interno para presentarlos de forma
     * elegante. Ejemplo de salida: "Enero/2026"
     */
    public String getFechaFormateada() {
        // CORRECCIÓN CRÍTICA: Accedemos al mes a través del objeto periodo encapsulado
        int mesNumerico = this.periodo.getMes();
        int anioNumerico = this.periodo.getAnio();

        if (mesNumerico >= 1 && mesNumerico <= 12) {
            // Se resta 1 porque los arreglos en Java inician en el índice 0 (Enero = índice 0)
            return NOMBRES_MESES[mesNumerico - 1] + "/" + anioNumerico;
        }
        return "Desconocido/" + anioNumerico;
    }

    /**
     * Mantiene intacta la escritura en base de datos de texto plano con
     * variables numéricas raw. Ejemplo en TXT: AGUA;15.2;1;2026;Óptimo
     */
    public String toTextoPlano() {
        return this.getTipoServicio() + ";"
                + this.valorConsumido + ";"
                + this.periodo.getMes() + ";"
                + this.periodo.getAnio() + ";"
                + this.estadoSostenibilidad;
    }

    // --- GETTERS Y SETTERS ---
    public double getValorConsumido() {
        return valorConsumido;
    }

    public void setValorConsumido(double valorConsumido) {
        this.valorConsumido = valorConsumido;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public String getEstadoSostenibilidad() {
        return estadoSostenibilidad;
    }

    public void setEstadoSostenibilidad(String estadoSostenibilidad) {
        this.estadoSostenibilidad = estadoSostenibilidad;
    }
}
