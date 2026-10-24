package modelo;

import java.util.ArrayList;
import java.util.List;

public class Comunidad {

    private String nombre;
    private String idComunidad;
    private String ubicacion;
    private int estrato;
    private List<Vivienda> listaViviendas;

    public Comunidad(String nombre, String idComunidad, String ubicacion, int estrato) {
        this.nombre = nombre;
        this.idComunidad = idComunidad;
        this.ubicacion = ubicacion;
        this.estrato = estrato;
        this.listaViviendas = new ArrayList<>();
    }

    public void agregarVivienda(Vivienda vivienda) {
        this.listaViviendas.add(vivienda);
    }

    public String toTextoPlano() {
        return this.idComunidad + ";" + this.nombre + ";" + this.ubicacion + ";" + this.estrato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(String idComunidad) {
        this.idComunidad = idComunidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public List<Vivienda> getListaViviendas() {
        return listaViviendas;
    }

    public void setListaViviendas(List<Vivienda> listaViviendas) {
        this.listaViviendas = listaViviendas;
    }
}
