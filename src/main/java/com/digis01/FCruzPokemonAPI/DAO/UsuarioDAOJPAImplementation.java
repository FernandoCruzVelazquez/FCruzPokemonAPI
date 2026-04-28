package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Favorito;
import com.digis01.FCruzPokemonAPI.JPA.Pokemon;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Rol;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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

            Usuario usuario = entityManager.find(Usuario.class, favorito.getUsuario().getIdusuario());
            if (usuario == null) {
                throw new RuntimeException("Usuario no existe");
            }

            Pokemon pokemon = entityManager.find(Pokemon.class, favorito.getPokemon().getIdpokemon());
            if (pokemon == null) {
                throw new RuntimeException("Pokemon no existe");
            }

            favoritoJPA.setUsuario(usuario);
            favoritoJPA.setPokemon(pokemon);

            entityManager.persist(favoritoJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return result;
    }

    @Override
    public Result UsuarioUpdate(Usuario usuario) {

        Result result = new Result();

        try {

            Usuario usuarioJPA = entityManager.find(Usuario.class, usuario.getIdusuario());

            if (usuarioJPA != null) {
                usuarioJPA.setNombreusuario(usuario.getNombreusuario());
                usuarioJPA.setApellidopaterno(usuario.getApellidopaterno());
                usuarioJPA.setApellidomaterno(usuario.getApellidomaterno());
                usuarioJPA.setUsername(usuario.getCorreo());
                usuarioJPA.setCorreo(usuario.getCorreo());
                usuarioJPA.setPassword(usuario.getPassword());

                Rol rolJPA = entityManager.find(Rol.class, usuario.getRol().getIdrol());
                usuarioJPA.setRol(rolJPA);

                result.correct = true;
            } else {
                result.correct = false;
                return result;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return result;

    }

    @Override
    public Result UsuarioDelete(Usuario usuario) {

        Result result = new Result();

        try {

            Usuario usuarioJPA = entityManager.find(Usuario.class, usuario.getIdusuario());

            if (usuarioJPA == null) {
                result.correct = false;
                return result;
            }

            entityManager.remove(usuarioJPA);
            result.correct = true;

        } catch (Exception ex) {
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return result;

    }

    @Override
    public Result UsuarioGetAll() {

        Result result = new Result();

        try {

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuarios = queryUsuario.getResultList();

            result.objects = new ArrayList<>();
            result.objects.addAll(usuarios);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return result;
    }

    @Override
    public Result UsuarioGetById(int idUsuario) {
        
        Result result = new Result();
        
        try {
            
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            
            if (usuario != null) {
                result.object = usuario;
                result.correct = true;
            } else {
                result.correct = false;
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
        
        return result;
        
    }

}
