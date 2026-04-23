package com.digis01.FCruzPokemonAPI.RestController;

import com.digis01.FCruzPokemonAPI.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    @PostMapping
    //@PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Result> UsuarioDireccionAdd(@RequestBody Usuario usuario) {

        Result result = new Result();

        try {

            result = usuarioDAOJPAImplementation.UsuarioAdd(usuario);

            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;

            return ResponseEntity.status(500).body(result);
        }

    }

}
