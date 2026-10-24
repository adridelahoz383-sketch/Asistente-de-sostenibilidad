package modelo;

public class ConsumoGas extends Consumo {

    private final double UMBRAL_GAS = 11.0;

    // Actualizamos los parámetros: ahora recibe Periodo
    public ConsumoGas(double valorConsumido, Periodo periodo) {
        super(valorConsumido, periodo);
        calcularEstadoSostenibilidad();
    }

    @Override
    public void calcularEstadoSostenibilidad() {
        if (this.valorConsumido <= UMBRAL_GAS) {
            this.estadoSostenibilidad = "Óptimo";
        } else if (this.valorConsumido <= UMBRAL_GAS * 1.25) {
            this.estadoSostenibilidad = "Elevado";
        } else {
            this.estadoSostenibilidad = "Extremo";
        }
    }

    @Override
    public String getTipoServicio() {
        return "GAS";
    }
}
