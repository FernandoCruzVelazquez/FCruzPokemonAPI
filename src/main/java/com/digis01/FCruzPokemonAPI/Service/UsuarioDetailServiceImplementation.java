package com.digis01.FCruzPokemonAPI.Service;

import com.digis01.FCruzPokemonAPI.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailServiceImplementation implements UserDetailsService {

    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = getUsuarioByUsername(username);

        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities("ROLE_" + usuario.getRol().getNombrerol())
                .build();
    }

    public Usuario getUsuarioByUsername(String username) {
        Result result = usuarioDAOJPAImplementation.GetByUserName(username);
        if (result.correct && result.object != null) {
            return (Usuario) result.object;
        } else {
            throw new UsernameNotFoundException("No se encontró el usuario: " + username);
        }
    }
}
