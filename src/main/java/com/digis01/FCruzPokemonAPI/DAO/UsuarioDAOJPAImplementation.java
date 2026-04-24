package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Favorito;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Rol;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UsuarioDAOJPAImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result GetByUserName(String userName) {

        Result result = new Result();

        try {

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario WHERE username = :pUserName", Usuario.class);
            queryUsuario.setParameter("pUserName", userName);

            result.object = queryUsuario.getSingleResult();
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return result;
    }

    @Override
    public Result UsuarioAdd(Usuario usuario) {
        Result result = new Result();

        Usuario usuarioJPA = new Usuario();
        usuarioJPA.setNombreusuario(usuario.getNombreusuario());
        usuarioJPA.setApellidopaterno(usuario.getApellidopaterno());
        usuarioJPA.setApellidomaterno(usuario.getApellidomaterno());
        usuarioJPA.setUsername(usuario.getUsername());
        usuarioJPA.setCorreo(usuario.getCorreo());
        usuarioJPA.setPassword(
                passwordEncoder.encode(usuario.getPassword())
        );

        Rol rolJPA = entityManager.getReference(Rol.class, usuario.getRol().getIdrol());
        usuarioJPA.setRol(rolJPA);

        entityManager.persist(usuarioJPA);

        result.correct = true;
        return result;

    }

    @Override
    public Result Favorito(Favorito favorito) {
        
        Result result = new Result();
        
        try {
            
            Favorito favoritoJPA = new Favorito();
            
            Usuario usuarioJPA = entityManager.getReference(Usuario.class, favorito.getUsuario().getIdusuario());
            favoritoJPA.setUsuario(usuarioJPA);
            
            
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return result;
    }

}
