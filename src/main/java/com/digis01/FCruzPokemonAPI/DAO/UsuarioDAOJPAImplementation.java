package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Rol;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UsuarioDAOJPAImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetByUserName(String userName) {

        Result result = new Result();

        try {

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario WHERE userName = :pUserName", Usuario.class);
            queryUsuario.setParameter("pUserName", userName);

            result.object = queryUsuario.getSingleResult();
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result UsuarioAdd(Usuario usuario) {

        Result result = new Result();

        try {
            Usuario usuarioJPA = new Usuario();
            usuarioJPA.setNombre(usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
            usuarioJPA.setUserName(usuario.getUserName());
            usuarioJPA.setCorreo(usuario.getCorreo());
            usuarioJPA.setPassword(usuario.getPassword());

            Rol rolJPA = entityManager.getReference(Rol.class, usuario.getRol().getIdRol());
            usuarioJPA.setRol(rolJPA);

            entityManager.persist(usuarioJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;

    }

}
