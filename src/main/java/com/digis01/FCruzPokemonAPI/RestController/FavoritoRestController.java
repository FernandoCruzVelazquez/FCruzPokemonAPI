package com.digis01.FCruzPokemonAPI.RestController;

import com.digis01.FCruzPokemonAPI.DAO.FavoritoDAOJPAImplementation;
import com.digis01.FCruzPokemonAPI.DTO.FavoritoDTO;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/favorito")
public class FavoritoRestController {

    @Autowired
    private FavoritoDAOJPAImplementation favoritoDAOJPAImplementation;

    @PostMapping
    public ResponseEntity<Result> FavoritoAdd(@RequestBody FavoritoDTO dto) {

        try {

            Result result = favoritoDAOJPAImplementation.FavoritoAdd(
                    dto.idUsuario,
                    dto.idPokemon,
                    dto.nombre,
                    dto.imagen
            );

            return result.correct
                    ? ResponseEntity.ok(result)
                    : ResponseEntity.badRequest().body(result);

        } catch (Exception ex) {
            Result result = new Result();
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

    @DeleteMapping
    public ResponseEntity<Result> FavoritoDelete(
            @RequestParam int idUsuario,
            @RequestParam int idPokemon) {

        Result result;

        try {

            result = favoritoDAOJPAImplementation.FavoritoDelete(idUsuario, idPokemon);

            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        } catch (Exception ex) {
            result = new Result();
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<Result> GetMisFavoritos(@PathVariable String username) {

        Result result;

        try {
            result = favoritoDAOJPAImplementation.GetMisFavoritos(username);

            return result.correct
                    ? ResponseEntity.ok(result)
                    : ResponseEntity.badRequest().body(result);

        } catch (Exception ex) {

            result = new Result();
            result.correct = false;
            result.errorMessage = ex.getMessage();

            return ResponseEntity.status(500).body(result);
        }
    }

}
