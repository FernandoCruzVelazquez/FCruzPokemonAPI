
package com.digis01.FCruzPokemonAPI.JPA;

public class HabilidadPokemon {
    
    private int idHP;
    public Pokemon pokemon;
    public Habilidad habilidad;
    
    public int getIdHP(){
        return idHP;
    }
    
    public void setIdHP(int idHP){
        this.idHP = idHP;
    }
    
}
