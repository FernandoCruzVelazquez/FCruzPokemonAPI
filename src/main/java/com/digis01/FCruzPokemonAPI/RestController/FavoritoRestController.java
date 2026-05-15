package com.digis01.FCruzPokemonAPI.RestController;

import com.digis01.FCruzPokemonAPI.DAO.FavoritoDAOJPAImplementation;
import com.digis01.FCruzPokemonAPI.DTO.FavoritoDTO;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/favorito")
@Tag(name = "Favoritos", description = "Endpoints para gestionar los Pokémones favoritos de los usuarios")
@SecurityRequirement(name = "JavaInUseSecurityScheme")
public class FavoritoRestController {

    @Autowired
    private FavoritoDAOJPAImplementation favoritoDAOJPAImplementation;

    @Operation(
        summary = "Agregar a favoritos",
        description = "Guarda un Pokémon en la lista de favoritos de un usuario. Requiere token JWT.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Agregado correctamente", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "Error en la validación de datos")
        }
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> FavoritoAdd(@RequestBody FavoritoDTO dto) {
        try {
            Result result = favoritoDAOJPAImplementation.FavoritoAdd(
                    dto.idUsuario, dto.idPokemon, dto.nombre, dto.imagen
            );
            return result.correct ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
        } catch (Exception ex) {
            Result result = new Result();
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

    @Operation(
        summary = "Eliminar de favoritos",
        description = "Elimina un Pokémon específico de los favoritos de un usuario mediante parámetros de consulta (Query Params).",
        responses = {
            @ApiResponse(responseCode = "200", description = "Eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "No se encontró el registro")
        }
    )
    @DeleteMapping
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> FavoritoDelete(
            @Parameter(description = "ID del usuario propietario", example = "1") @RequestParam int idUsuario,
            @Parameter(description = "ID del Pokémon a eliminar", example = "25") @RequestParam int idPokemon) {

        Result result;
        try {
            result = favoritoDAOJPAImplementation.FavoritoDelete(idUsuario, idPokemon);
            return result.correct ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
        } catch (Exception ex) {
            result = new Result();
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

    @Operation(
        summary = "Obtener favoritos por nombre de usuario",
        description = "Recupera la lista de Pokémones favoritos de un usuario específico.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida (revisar objeto 'objects' en el resultado)")
        }
    )
    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> GetMisFavoritos(
            @Parameter(description = "Nombre de usuario (username)", example = "fvelazquez") 
            @PathVariable String username) {

        Result result;
        try {
            result = favoritoDAOJPAImplementation.GetMisFavoritos(username);
            return result.correct ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
        } catch (Exception ex) {
            result = new Result();
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(500).body(result);
        }
    }
}