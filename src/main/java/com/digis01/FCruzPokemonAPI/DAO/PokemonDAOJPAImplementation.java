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

            entityManager.merge(pokemon);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
        }

        return result;
    }
}
