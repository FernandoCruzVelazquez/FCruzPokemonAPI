package com.digis01.FCruzPokemonAPI.JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpokemon;
    private String nombrepokemon;
    private double altura;
    private double peso;
    private int experienciabase;
    private String imagen;

    public int getIdpokemon() {
        return idpokemon;
    }

    public void setIdpokemon(int idpokemon) {
        this.idpokemon = idpokemon;
    }

    public String getNombrepokemon() {
        return nombrepokemon;
    }

    public void setNombrepokemon(String nombrepokemon) {
        this.nombrepokemon = nombrepokemon;
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

    public int getExperienciabase() {
        return experienciabase;
    }

    public void setExperienciabase(int experienciabase) {
        this.experienciabase = experienciabase;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
