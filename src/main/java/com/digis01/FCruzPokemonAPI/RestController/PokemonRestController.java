package com.digis01.FCruzPokemonAPI.RestController;

import com.digis01.FCruzPokemonAPI.DAO.PokemonDAOJPAImplementation;
import com.digis01.FCruzPokemonAPI.JPA.Pokemon;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pokemon")
@Tag(name = "Pokémon", description = "Operaciones relacionadas con la gestión de Pokémones")
@SecurityRequirement(name = "JavaInUseSecurityScheme") 
public class PokemonRestController {
    
    @Autowired
    private PokemonDAOJPAImplementation pokemonDAOJPAImplementation;
    
    @Operation(
        summary = "Registrar un nuevo Pokémon",
        description = "Añade un nuevo Pokémon a la base de datos. **Roles permitidos:** Profesor, Maestro, Entrenador.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Pokémon registrado exitosamente",
                content = @Content(schema = @Schema(implementation = Result.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos suficientes (Rol incorrecto)"),
            @ApiResponse(responseCode = "401", description = "No estás autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
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
}