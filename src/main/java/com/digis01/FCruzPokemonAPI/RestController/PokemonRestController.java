
package com.digis01.FCruzPokemonAPI.RestController;

import com.digis01.FCruzPokemonAPI.DAO.PokemonDAOJPAImplementation;
import com.digis01.FCruzPokemonAPI.JPA.Pokemon;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pokemon")
public class PokemonRestController {
    
    @Autowired
    private PokemonDAOJPAImplementation pokemonDAOJPAImplementation;
    
    @PostMapping
    public ResponseEntity<Result> PokemonAdd(@RequestBody Pokemon pokemon){
        
        Result result = new Result();
        
        try {
            
            result = pokemonDAOJPAImplementation.PokemonAdd(pokemon);
            
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return ResponseEntity.status(500).body(result);
        }
        
    }
    
    @PutMapping("/updatePokemon")
    public ResponseEntity<Result> PokemonUpdate(@RequestBody Pokemon pokemon){
        
        Result result = new Result();
        
        try {
            
            result = pokemonDAOJPAImplementation.PokemonUpdate(pokemon);
            
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return ResponseEntity.status(500).body(result);
        }
        
    }
    
    @DeleteMapping("/deletePokemon/{idPokemon}")
    public ResponseEntity PokemonDelete(@PathVariable int idPokemon){
        Result result = new Result();
        
        try {
            
            Pokemon pokemon = new Pokemon();
            pokemon.setIdpokemon(idPokemon);
            
            result = pokemonDAOJPAImplementation.PokemonDelete(pokemon);
            
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return ResponseEntity.status(500).body(result);
        }
    }
    
}
