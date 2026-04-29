package com.digis01.FCruzPokemonAPI.DAO;

import com.digis01.FCruzPokemonAPI.JPA.Pokemon;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class PokemonDAOJPAImplementation implements IPokemon {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result PokemonAdd(Pokemon pokemon) {

        Result result = new Result();

        try {

            Pokemon pokemonJPA = new Pokemon();

            pokemonJPA.setNombrepokemon(pokemon.getNombrepokemon());
            pokemonJPA.setAltura(pokemon.getAltura());
            pokemonJPA.setPeso(pokemon.getPeso());
            pokemonJPA.setExperienciabase(pokemon.getExperienciabase());
            pokemonJPA.setImagen(pokemon.getImagen());

            entityManager.persist(pokemonJPA);
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return result;
    }

    @Override
    public Result PokemonUpdate(Pokemon pokemon) {

        Result result = new Result();

        try {

            Pokemon pokemonJPA = entityManager.find(Pokemon.class, pokemon.getIdpokemon());

            if (pokemonJPA != null) {
                pokemonJPA.setNombrepokemon(pokemon.getNombrepokemon());
                pokemonJPA.setAltura(pokemon.getAltura());
                pokemonJPA.setPeso(pokemon.getPeso());
                pokemonJPA.setExperienciabase(pokemon.getExperienciabase());
                pokemonJPA.setImagen(pokemon.getImagen());

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
    public Result PokemonDelete(Pokemon pokemon) {

        Result result = new Result();

        try {

            Pokemon pokemonJPA = entityManager.find(Pokemon.class, pokemon.getIdpokemon());

            if (pokemonJPA == null) {
                result.correct = false;
                return result;
            }

            entityManager.remove(pokemonJPA);
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
        return result;
    }

}
