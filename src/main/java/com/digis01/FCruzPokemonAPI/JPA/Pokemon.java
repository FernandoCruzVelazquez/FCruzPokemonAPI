package com.digis01.FCruzPokemonAPI.JPA;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Schema(description = "Modelo que representa la información básica de un Pokémon")
public class Pokemon {

    @Id
    @Schema(description = "ID oficial del Pokémon (según la PokeAPI)", example = "25")
    private int idpokemon;

    @Schema(description = "Nombre del Pokémon", example = "Pikachu")
    private String nombrepokemon;

    @Schema(description = "URL de la imagen del sprite del Pokémon", example = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png")
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
