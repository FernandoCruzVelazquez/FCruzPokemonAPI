package com.digis01.FCruzPokemonAPI.DAO;

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
        usuarioJPA.setImagen(usuario.getImagen());

        Rol rolJPA = entityManager.getReference(Rol.class, usuario.getRol().getIdrol());
        usuarioJPA.setRol(rolJPA);

        entityManager.persist(usuarioJPA);

        result.correct = true;
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
                usuarioJPA.setUsername(usuario.getUsername());
                usuarioJPA.setCorreo(usuario.getCorreo());
                usuarioJPA.setImagen(usuario.getImagen());

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

    @Override
    public Result ActivarUsuario(String correo) {

        Result result = new Result();

        try {

            TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario WHERE correo = :correo", Usuario.class);
            query.setParameter("correo", correo);
            Usuario usuario = query.getSingleResult();

            if (usuario != null) {
                usuario.setEstado(1);
                entityManager.merge(usuario);
                result.correct = true;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return result;

    }

    @Override
    public Result ActualizarPassword(String correo, String nuevaPassword) {

        Result result = new Result();
        try {
            TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario WHERE correo = :correo", Usuario.class);
            query.setParameter("correo", correo);
            Usuario usuario = query.getSingleResult();

            if (usuario != null) {
                usuario.setPassword(passwordEncoder.encode(nuevaPassword));
                entityManager.merge(usuario);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "El usuario no existe.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return result;
    }
    
    @Override
    public Result ActivacionUsuario(String correo) {
        
        Result result = new Result();
        
        try {
            
            TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario WHERE correo = :correo", Usuario.class);
            query.setParameter("correo", correo);
            Usuario usuario = query.getSingleResult();
            
            if (usuario != null) {
                usuario. setActivacion(1);
                entityManager.merge(usuario);
                result.correct = true;
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
        
        return result;
        
    }
    
    @Override
    public Result DesactivacionUsuario(String correo) {
        
        Result result = new Result();
        
        try {
            
            TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario WHERE correo = :correo", Usuario.class);
            query.setParameter("correo", correo);
            Usuario usuario = query.getSingleResult();
            
            if (usuario != null) {
                usuario. setActivacion(0);
                entityManager.merge(usuario);
                result.correct = true;
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
        
        return result;
        
    }
    
    @Override
    public Result cambiarPasswordDirecto(String correo, String nuevaPassword) {
        Result result = new Result();
        try {
            int filasAfectadas = entityManager.createQuery(
                "UPDATE Usuario u SET u.password = :pass WHERE u.correo = :correo")
                .setParameter("pass", passwordEncoder.encode(nuevaPassword))
                .setParameter("correo", correo)
                .executeUpdate();

            if (filasAfectadas > 0) {
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se encontró el usuario para actualizar.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al cambiar password: " + ex.getMessage();
        }
        return result;
    }

}
