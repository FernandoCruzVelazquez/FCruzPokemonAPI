package com.digis01.FCruzPokemonAPI.JPA;

public class MovimientoPokemon {

    private int idMP;
    public Pokemon pokemon;
    public Movimiento movimiento;

    public int getIdMP() {
        return idMP;
    }

    public void setIdMP(int idMP) {
        this.idMP = idMP;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

}
