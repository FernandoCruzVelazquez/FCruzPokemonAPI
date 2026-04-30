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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/favorito")
public class FavoritoRestController {

    @Autowired
    private FavoritoDAOJPAImplementation favoritoDAOJPAImplementation;

    @PostMapping
    public ResponseEntity<Result> FavoritoAdd(@RequestBody FavoritoDTO dto) {

        Result result = new Result();

        try {

            result = favoritoDAOJPAImplementation.FavoritoAdd(
                    dto.idUsuario,
                    dto.idPokemon,
                    dto.nombre,
                    dto.imagen
            );

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

    @DeleteMapping("/{idFavorito}")
    public ResponseEntity<Result> FavoritoDelete(@PathVariable int idFavorito) {

        Result result = new Result();

        try {
            result = favoritoDAOJPAImplementation.FavoritoDelete(idFavorito);

            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return ResponseEntity.badRequest().body(result);
        }

    }

    @GetMapping("/{username}")
    public ResponseEntity<Result> GetMisFavoritos(@PathVariable String username) {
        Result result = new Result();

        try {

            result = favoritoDAOJPAImplementation.GetMisFavoritos(username);

            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(result);
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

}
