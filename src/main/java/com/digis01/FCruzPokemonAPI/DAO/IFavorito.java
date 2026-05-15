
package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Result;

public interface IFavorito {
    
    Result FavoritoAdd(int idUsuario, int idPokemon, String nombre, String imagen);
    Result FavoritoDelete(int idUsuario, int idFavorito);
    Result GetMisFavoritos(String username);
    Result GetRankingPokemon();
    
}
