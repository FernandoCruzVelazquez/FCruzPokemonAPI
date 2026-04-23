package com.digis01.FCruzPokemonAPI.RestController;

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
public class AuthRestConroller {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDetailServiceImplementation usuarioDetailService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.get("userName"),
                loginRequest.get("Password")));

        UserDetails user = usuarioDetailService.loadUserByUsername(loginRequest.get("userName"));
        String token = jwtService.generateToken(user);
        Map<String, Object> map = new HashMap<>();
        map.put("Key", token);
        return ResponseEntity.ok(map);
    }

}
