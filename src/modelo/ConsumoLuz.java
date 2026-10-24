package modelo;

public class ConsumoLuz extends Consumo {

    private final double UMBRAL_LUZ = 173.0;

    // Actualizamos los parámetros: ahora recibe Periodo
    public ConsumoLuz(double valorConsumido, Periodo periodo) {
        super(valorConsumido, periodo);
        calcularEstadoSostenibilidad();
    }

    @Override
    public void calcularEstadoSostenibilidad() {
        if (this.valorConsumido <= UMBRAL_LUZ) {
            this.estadoSostenibilidad = "Óptimo";
        } else if (this.valorConsumido <= UMBRAL_LUZ * 1.25) {
            this.estadoSostenibilidad = "Elevado";
        } else {
            this.estadoSostenibilidad = "Extremo";
        }
    }

    @Override
    public String getTipoServicio() {
        return "ENERGIA";
    }
}
