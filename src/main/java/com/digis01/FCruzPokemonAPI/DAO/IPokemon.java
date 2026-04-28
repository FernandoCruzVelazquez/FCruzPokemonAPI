
package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Pokemon;
import com.digis01.FCruzPokemonAPI.JPA.Result;

public interface IPokemon {
    
    Result PokemonAdd(Pokemon pokemon);
    Result PokemonUpdate(Pokemon pokemon);
    
}
