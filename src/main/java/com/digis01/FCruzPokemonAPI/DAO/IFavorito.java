
package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Favorito;
import com.digis01.FCruzPokemonAPI.JPA.Result;

public interface IFavorito {
    
    Result Favorito(Favorito favorito);
    Result FavoritoDelete(Favorito favorito);
    
}
