
package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;

public interface IUsuarioJPA {
    
    Result UsuarioGetAll();
    Result UsuarioGetById(int idUsuario);
    Result UsuarioAdd(Usuario usuario);
    Result UsuarioUpdate(Usuario usuario);
    Result UsuarioDelete(Usuario usuario);
    Result GetByUserName(String userName);
    Result ActivarUsuario(String correo);
    Result ActivacionUsuario(String correo);
    Result DesactivacionUsuario(String correo);
    Result ActualizarPassword(String correo, String nuevaPassword);
    
}
