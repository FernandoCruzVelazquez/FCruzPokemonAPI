
package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Favorito;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;

public interface IUsuarioJPA {
    
    Result UsuarioAdd(Usuario usuario);
    Result GetByUserName(String userName);
    Result Favorito(Favorito favorito);
    
}
