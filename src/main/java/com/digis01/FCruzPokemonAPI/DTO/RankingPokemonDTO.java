
package com.digis01.FCruzPokemonAPI.DTO;

import com.digis01.FCruzPokemonAPI.JPA.Pokemon;

public class RankingPokemonDTO {

    private Pokemon pokemon;
    private Long totalVotos;

    public RankingPokemonDTO(Pokemon pokemon, Long totalVotos) {
        this.pokemon = pokemon;
        this.totalVotos = totalVotos;
    }

    // Getters y Setters
    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Long getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Long totalVotos) {
        this.totalVotos = totalVotos;
    }
}
