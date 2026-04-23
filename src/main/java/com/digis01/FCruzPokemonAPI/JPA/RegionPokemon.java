
package com.digis01.FCruzPokemonAPI.JPA;

public class RegionPokemon {
    
    private int idRP;
    public Pokemon idPokemon;
    public Region idRegion;

    public int getIdRP() {
        return idRP;
    }

    public void setIdRP(int idRP) {
        this.idRP = idRP;
    }

    public Pokemon getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(Pokemon idPokemon) {
        this.idPokemon = idPokemon;
    }

    public Region getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Region idRegion) {
        this.idRegion = idRegion;
    }
    
    
    
}
