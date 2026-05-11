package com.digis01.FCruzPokemonAPI.RestController;

import com.digis01.FCruzPokemonAPI.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private JavaMailSender mailSender;

    private static final ConcurrentHashMap<String, String> memoryCodes = new ConcurrentHashMap<>();

    
    
    @PostMapping
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> UsuarioAdd(@RequestBody Usuario usuario) {

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

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> UsuarioUpdate(@RequestBody Usuario usuario) {
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
    @PreAuthorize("hasRole('Profesor')")
    public ResponseEntity UsuarioDelete(@PathVariable int idUsuario) {

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
    @PreAuthorize("hasRole('Profesor')")
    public ResponseEntity usuarioGetAll() {
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
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<?> UsuarioGetById(@PathVariable int idUsuario) {

        try {
            Result result = usuarioDAOJPAImplementation.UsuarioGetById(idUsuario);

            if (result.correct) {
                if (result.object != null) {
                    return ResponseEntity.ok(result);
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

    @PostMapping("/enviar-validacion/{correo}")
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> enviarCodigo(@PathVariable String correo) {

        Result result = new Result();
        try {

            String codigo = String.valueOf((int) (Math.random() * 900000) + 100000);
            memoryCodes.put(correo, codigo);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("Verificación de cuenta - PokeAPI");
            message.setText("¡Hola! Tu código de confirmación es: " + codigo
                    + "\nPor favor, ingresalo en la aplicación para activar tu cuenta.");

            mailSender.send(message);

            result.correct = true;
            result.object = "Código enviado a " + correo;
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al enviar correo: " + ex.getLocalizedMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

    @PostMapping("/confirmar-codigo")
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> confirmarCodigo(@RequestBody Map<String, String> datos) {

        String correo = datos.get("correo");
        String codigoUsuario = datos.get("codigo");
        Result result = new Result();

        if (memoryCodes.containsKey(correo) && memoryCodes.get(correo).equals(codigoUsuario)) {
            result = usuarioDAOJPAImplementation.ActivarUsuario(correo);

            if (result.correct) {
                memoryCodes.remove(correo);
                result.object = "¡Cuenta activada con éxito!";
                return ResponseEntity.ok(result);
            }
        }

        result.correct = false;
        result.errorMessage = "El código es incorrecto o ya expiró";
        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/bienvenida/{correo}")
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> enviarBienvenida(@PathVariable String correo) {
        Result result = new Result();
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("¡Bienvenido Entrenador! - PokeAPI - F&F");
            message.setText("¡Hola!\n\nTu cuenta en PokeAPI - F&F se ha creado con éxito. "
                    + "Estamos felices de tenerte en nuestra comunidad.\n"
                    + "¡Prepárate para tu aventura Pokémon!");

            mailSender.send(message);

            result.correct = true;
            result.object = "Correo de bienvenida enviado a " + correo;
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al enviar bienvenida: " + ex.getMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

    @PostMapping("/enviar-validacionPASS/{correo}")
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> enviarCodigoPASS(@PathVariable String correo) {

        Result result = new Result();
        try {

            String codigo = String.valueOf((int) (Math.random() * 900000) + 100000);
            memoryCodes.put(correo, codigo);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("Verificación de cuenta - PokeAPI");
            message.setText("¡Hola! Tu código de confirmación es: " + codigo
                    + "\nPor favor, ingresalo en la aplicación para actualizar tu contraseña.");

            mailSender.send(message);

            result.correct = true;
            result.object = "Código enviado a " + correo;
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al enviar correo: " + ex.getLocalizedMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

    @PostMapping("/confirmar-codigo-pass")
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> confirmarCodigoPASS(@RequestBody Map<String, String> datos) {
        String correo = datos.get("correo");
        String codigoUsuario = datos.get("codigo");
        Result result = new Result();

        if (memoryCodes.containsKey(correo) && memoryCodes.get(correo).equals(codigoUsuario)) {
            memoryCodes.remove(correo);
            result.correct = true;
            result.object = "Código validado correctamente";
            return ResponseEntity.ok(result);
        }

        result.correct = false;
        result.errorMessage = "El código es incorrecto o ya expiró";
        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/updatePassword")
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> ActualizarPassword(@RequestBody Usuario usuario) {
        Result result = new Result();

        try {
            result = usuarioDAOJPAImplementation.ActualizarPassword(usuario.getCorreo(), usuario.getPassword());

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