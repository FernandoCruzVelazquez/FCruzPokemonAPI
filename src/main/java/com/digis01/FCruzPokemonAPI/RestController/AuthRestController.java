package com.digis01.FCruzPokemonAPI.RestController;

import com.digis01.FCruzPokemonAPI.Service.JwtService;
import com.digis01.FCruzPokemonAPI.Service.UsuarioDetailServiceImplementation;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para el manejo de sesiones y tokens JWT")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDetailServiceImplementation usuarioDetailService;

    @Autowired
    private JwtService jwtService;

    @Operation(
        summary = "Inicio de sesión",
        description = "Autentica a un usuario y devuelve un token JWT junto con su información básica.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Autenticación exitosa",
                content = @Content(mediaType = "application/json", 
                examples = @ExampleObject(value = "{ \"token\": \"eyJhbG...\", \"username\": \"pika\", \"idusuario\": 1, \"nombreCompleto\": \"Ash Ketchum\" }"))
            ),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content)
        }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credenciales del usuario",
            required = true,
            content = @Content(
                examples = @ExampleObject(value = "{ \"username\": \"usuario123\", \"password\": \"password123\" }")
            )
        )
        @RequestBody Map<String, String> loginRequest) {

        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails = usuarioDetailService.loadUserByUsername(username);
        String token = jwtService.generateToken(userDetails);
        Usuario usuario = usuarioDetailService.getUsuarioByUsername(username);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", usuario.getUsername());
        response.put("idusuario", usuario.getIdusuario()); 
        response.put("nombreCompleto", usuario.getNombreusuario());

        return ResponseEntity.ok(response);
    }
}