package com.digis01.FCruzPokemonAPI.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idfavoritos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpokemon")
    public Pokemon pokemon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    @JsonIgnore
    public Usuario usuario;

    public int getIdfavoritos() {
        return idfavoritos;
    }

    public void setIdfavoritos(int idfavoritos) {
        this.idfavoritos = idfavoritos;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
