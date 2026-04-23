package com.digis01.FCruzPokemonAPI.JPA;

public class Gimnacio {

    private int idGYM;
    private String nombreGimnacio;
    private String descripcion;
    public Region region;

    public int getIdGYM() {
        return idGYM;
    }

    public void setIdGYM(int idGYM) {
        this.idGYM = idGYM;
    }

    public String getNombreGimnacio() {
        return nombreGimnacio;
    }

    public void setNombreGimnacio(String nombreGimnacio) {
        this.nombreGimnacio = nombreGimnacio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
