package com.digis01.FCruzPokemonAPI.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Schema(description = "Modelo que representa la relación de un Pokémon marcado como favorito por un usuario")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del registro de favorito", example = "101")
    private int idfavoritos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpokemon")
    @Schema(description = "Información del Pokémon marcado como favorito")
    public Pokemon pokemon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    @JsonIgnore
    @Schema(description = "Usuario propietario del favorito", hidden = true)
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
