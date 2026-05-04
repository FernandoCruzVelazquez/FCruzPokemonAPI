package com.digis01.FCruzPokemonAPI.RestController;

import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import com.digis01.FCruzPokemonAPI.Service.JwtService;
import com.digis01.FCruzPokemonAPI.Service.UsuarioDetailServiceImplementation;
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

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDetailServiceImplementation usuarioDetailService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {

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
