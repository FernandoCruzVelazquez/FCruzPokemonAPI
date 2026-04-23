
package com.digis01.FCruzPokemonAPI.JPA;

public class Pokemon {
    
    private int idPokemon;
    private String nombrePokemon;
    private double altura;
    private double peso;
    private int experienciaBase;
    private String imagen;

    public int getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(int idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getNombre() {
        return nombrePokemon;
    }

    public void setNombre(String nombre) {
        this.nombrePokemon = nombre;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getExperienciaBase() {
        return experienciaBase;
    }

    public void setExperienciaBase(int experienciaBase) {
        this.experienciaBase = experienciaBase;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    
    
}
