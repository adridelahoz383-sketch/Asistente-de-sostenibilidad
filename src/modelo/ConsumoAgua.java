package modelo;

public class ConsumoAgua extends Consumo {

    private final double UMBRAL_AGUA = 16.0;

    // Actualizamos los parámetros: ahora recibe Periodo
    public ConsumoAgua(double valorConsumido, Periodo periodo) {
        super(valorConsumido, periodo); // Se lo enviamos al nuevo constructor padre
        calcularEstadoSostenibilidad();
    }

    @Override
    public void calcularEstadoSostenibilidad() {
        if (this.valorConsumido <= UMBRAL_AGUA) {
            this.estadoSostenibilidad = "Óptimo";
        } else if (this.valorConsumido <= UMBRAL_AGUA * 1.25) {
            this.estadoSostenibilidad = "Elevado";
        } else {
            this.estadoSostenibilidad = "Extremo";
        }
    }

    @Override
    public String getTipoServicio() {
        return "AGUA";
    }
}