package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.DTO.RankingPokemonDTO;
import com.digis01.FCruzPokemonAPI.JPA.Favorito;
import com.digis01.FCruzPokemonAPI.JPA.Pokemon;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
            if (usuario == null) {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
                return result;
            }

            String jpql = "SELECT COUNT(f) FROM Favorito f WHERE f.usuario.idusuario = :uid AND f.pokemon.idpokemon = :pid";

            Long count = entityManager.createQuery(jpql, Long.class)
                    .setParameter("uid", idUsuario)
                    .setParameter("pid", idPokemon)
                    .getSingleResult();

            if (count > 0) {
                result.correct = true;
                result.errorMessage = "El Pokémon ya está en favoritos";
                return result;
            }

            Pokemon pokemon = entityManager.find(Pokemon.class, idPokemon);

            if (pokemon == null) {
                pokemon = new Pokemon();
                pokemon.setIdpokemon(idPokemon);
                pokemon.setNombrepokemon(nombre);
                pokemon.setImagen(imagen);

                entityManager.persist(pokemon);
            }

            // 4. Crear favorito
            Favorito favorito = new Favorito();
            favorito.setUsuario(usuario);
            favorito.setPokemon(pokemon);

            entityManager.persist(favorito);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage(); // mejor que localized
        }
        return result;
    }

    @Override
    public Result FavoritoDelete(int idUsuario, int idPokemon) {

        Result result = new Result();

        try {

            String jpql = "DELETE FROM Favorito f WHERE f.usuario.idusuario = :uid AND f.pokemon.idpokemon = :pid";

            int rows = entityManager.createQuery(jpql)
                    .setParameter("uid", idUsuario)
                    .setParameter("pid", idPokemon)
                    .executeUpdate();

            if (rows > 0) {
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "El favorito no existe";
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
        }
        return result;
    }

    @Override
    public Result GetMisFavoritos(String username) {

        Result result = new Result();

        try {

            String consulta = "SELECT f FROM Favorito f\n"
                    + "JOIN FETCH f.pokemon\n"
                    + "JOIN FETCH f.usuario\n"
                    + "WHERE LOWER(f.usuario.username) = LOWER(:user)";

            List<Favorito> lista = entityManager
                    .createQuery(consulta, Favorito.class)
                    .setParameter("user", username)
                    .getResultList();

            result.objects = new ArrayList<>(lista);
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Result GetRankingPokemon() {

        Result result = new Result();

        try {
            String consulta = "SELECT new com.digis01.FCruzPokemonAPI.DTO.RankingPokemonDTO(f.pokemon, COUNT(f.pokemon.idpokemon)) "
                    + "FROM Favorito f "
                    + "GROUP BY f.pokemon " 
                    + "ORDER BY COUNT(f.pokemon.idpokemon) DESC";

            List<RankingPokemonDTO> lista = entityManager
                    .createQuery(consulta, RankingPokemonDTO.class)
                    .setMaxResults(10)
                    .getResultList();

            result.objects = new ArrayList<>(lista);
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            ex.printStackTrace(); // Mantenemos el rastro por si acaso
        }
        return result;
    }

}
