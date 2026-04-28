package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Favorito;
import com.digis01.FCruzPokemonAPI.JPA.Pokemon;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class FavoritoDAOJPAImplementation implements IFavorito {
    
    @Autowired
    private EntityManager entityManager;

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
    public Result FavoritoDelete(Favorito favorito) {
        Result result = new Result();
        
        try {
            
            Favorito favoritoJPA = entityManager.find(Favorito.class, favorito.getIdfavoritos());
            
            if (favoritoJPA == null) {
                result.correct = false;
                return result;
            }
            
            entityManager.remove(favoritoJPA);
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
        
        return result;
    }

}
