package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Favorito;
import com.digis01.FCruzPokemonAPI.JPA.Pokemon;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class FavoritoDAOJPAImplementation implements IFavorito {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result FavoritoAdd(int idUsuario, int idPokemon, String nombre, String imagen) {

        Result result = new Result();

        try {

            Usuario usuario = entityManager.find(Usuario.class, idUsuario);

            Pokemon pokemon = entityManager.find(Pokemon.class, idPokemon);

            if (pokemon == null) {
                pokemon = new Pokemon();
                pokemon.setIdpokemon(idPokemon);
                pokemon.setNombrepokemon(nombre);
                pokemon.setImagen(imagen);

                entityManager.persist(pokemon);
            }

            Favorito favorito = new Favorito();
            favorito.setUsuario(usuario);
            favorito.setPokemon(pokemon);

            entityManager.persist(favorito);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return result;
    }

    @Override
    public Result FavoritoDelete(int idFavorito) {

        Result result = new Result();

        try {

            Favorito favorito = entityManager.find(Favorito.class, idFavorito);
            if (favorito != null) {
                entityManager.remove(favorito);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "El pokemon favorito no existe";
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return result;
    }

    @Override
    public Result GetMisFavoritos(String username) {

        Result result = new Result();

        try {

            String consulta = "FROM Favorito f WHERE f.usuario.username = :user";
            List<Favorito> lista = entityManager.createQuery(consulta, Favorito.class).setParameter("user", username).getResultList();

            result.objects = new ArrayList<>(lista);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return result;
    }

}
