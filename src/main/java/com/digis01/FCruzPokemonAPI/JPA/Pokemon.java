package com.digis01.FCruzPokemonAPI.JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pokemon {

    @Id
    private int idpokemon;
    private String nombrepokemon;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
