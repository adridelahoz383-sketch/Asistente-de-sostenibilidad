package modelo;

import java.util.ArrayList;
import java.util.List;

public class Vivienda {

    private Comunidad comunidad;
    private String idVivienda;
    private double areaVivienda;
    private String nombrePropietario;
    private String cedulaPropietario;
    private String direccion;
    private List<Consumo> listaConsumos;

    public Vivienda(Comunidad comunidad, String idVivienda, double areaVivienda,
            String nombrePropietario, String cedulaPropietario, String direccion) {
        this.comunidad = comunidad;
        this.idVivienda = idVivienda;
        this.areaVivienda = areaVivienda;
        this.nombrePropietario = nombrePropietario;
        this.cedulaPropietario = cedulaPropietario;
        this.direccion = direccion;
        this.listaConsumos = new ArrayList<>();
    }

    public void agregarConsumo(Consumo consumo) {
        this.listaConsumos.add(consumo);
    }

    // --- NUEVO MÉTODO DE UTILIDAD DE NEGOCIO ---
    // Nos permite saber si esta vivienda ya registró un servicio (ej: AGUA) en un periodo (ej: 05/2026)
    public Consumo obtenerConsumoPorPeriodoYServicio(int mes, int anio, String tipoServicio) {
        for (Consumo con : listaConsumos) {
            // Usamos el método esIgualA que creamos en la clase Periodo
            if (con.getPeriodo().esIgualA(mes, anio) && con.getTipoServicio().equalsIgnoreCase(tipoServicio)) {
                return con; // Encontró el consumo duplicado o existente
            }
        }
        return null; // No existe registro previo
    }

    public String toTextoPlano() {
        return this.idVivienda + ";"
                + this.comunidad.getIdComunidad() + ";"
                + this.areaVivienda + ";"
                + this.nombrePropietario + ";"
                + this.cedulaPropietario + ";"
                + this.direccion;
    }

    // --- GETTERS Y SETTERS ---
    public List<Consumo> getStaticListaConsumos() { // Tu método para obtener la lista completa
        return listaConsumos;
    }

    public List<Consumo> getListaConsumos() {
        return listaConsumos;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public String getIdVivienda() {
        return idVivienda;
    }

    public void setIdVivienda(String idVivienda) {
        this.idVivienda = idVivienda;
    }

    public double getAreaVivienda() {
        return areaVivienda;
    }

    public void setAreaVivienda(double areaVivienda) {
        this.areaVivienda = areaVivienda;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public String getCedulaPropietario() {
        return cedulaPropietario;
    }

    public void setCedulaPropietario(String cedulaPropietario) {
        this.cedulaPropietario = cedulaPropietario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean existeConsumo(String tipoServicio, Periodo periodo) {
        if (this.listaConsumos == null) {
            return false;
        }

        for (Consumo consumo : this.listaConsumos) {
            // Evaluamos la coincidencia del servicio ignorando mayúsculas/minúsculas
            if (consumo.getTipoServicio().equalsIgnoreCase(tipoServicio)) {
                // Evaluamos que coincida el mes y el año encapsulado en el Periodo
                if (consumo.getPeriodo().getMes() == periodo.getMes()
                        && consumo.getPeriodo().getAnio() == periodo.getAnio()) {
                    return true; // Se encontró un duplicado exacto
                }
            }
        }
        return false; // El espacio está libre para escribir
    }
}
