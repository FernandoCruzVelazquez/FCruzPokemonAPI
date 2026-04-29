package com.digis01.FCruzPokemonAPI.RestController;

import com.digis01.FCruzPokemonAPI.DAO.FavoritoDAOJPAImplementation;
import com.digis01.FCruzPokemonAPI.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzPokemonAPI.JPA.Favorito;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    @Autowired
    private FavoritoDAOJPAImplementation favoritoDAOJPAImplementation;

    @PostMapping
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
            return ResponseEntity.status(500).body(result);
        }
    }
    
    @PostMapping("/favorito")
    public ResponseEntity<Result> FavoritoAdd(@RequestBody Favorito favorito){
        
        Result result = new Result();
        
        try {
            
            result = favoritoDAOJPAImplementation.Favorito(favorito);
            
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
    
    @PutMapping("/update")
    public ResponseEntity<Result> UsuarioUpdate(@RequestBody Usuario usuario){
        Result result = new Result();
        
        try {
            
            result = usuarioDAOJPAImplementation.UsuarioUpdate(usuario);
            
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
    
    @DeleteMapping("/deleteUsuario/{idUsuario}")
    public ResponseEntity UsuarioDelete(@PathVariable int idUsuario){
        
        try {
            
            Usuario usuario = new Usuario();
            usuario.setIdusuario(idUsuario);
            
            Result result = usuarioDAOJPAImplementation.UsuarioDelete(usuario);
            
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
            
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getLocalizedMessage());
        }
    }
    
    @GetMapping("/usuarios")
    public ResponseEntity usuarioGetAll(){
        Result result = new Result();
        
        try {
            
            result = usuarioDAOJPAImplementation.UsuarioGetAll();
            if (result.correct) {
                if (result.objects != null && !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result);
            }
            
        } catch (Exception ex) {
            result.correct = false;
            return ResponseEntity.status(500).body(result);
        }
    }
    
    @GetMapping("/perfil/{idUsuario}")
    public ResponseEntity<?> UsuarioGetById(@PathVariable int idUsuario){
        
        try {
            Result result = usuarioDAOJPAImplementation.UsuarioGetById(idUsuario);
            
            if (result.correct) {
                if (result.object != null) {
                    return ResponseEntity.ok(result.object);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex);
        }
    }

}
