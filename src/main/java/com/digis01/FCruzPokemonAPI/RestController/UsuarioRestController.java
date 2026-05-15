package com.digis01.FCruzPokemonAPI.RestController;

import com.digis01.FCruzPokemonAPI.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzPokemonAPI.JPA.Result;
import com.digis01.FCruzPokemonAPI.JPA.Usuario;
import java.net.URI;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api/usuario")
@Tag(name = "Usuarios", description = "Gestión de perfiles, activación de cuentas y recuperación de contraseñas")
@SecurityRequirement(name = "JavaInUseSecurityScheme")
public class UsuarioRestController {

    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    @Autowired
    private JavaMailSender mailSender;

    private static final ConcurrentHashMap<String, String> memoryCodes = new ConcurrentHashMap<>();

    
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en el sistema.")
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
    
    @Operation(summary = "Actualizar perfil", description = "Modifica los datos del usuario.")
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
    
    @Operation(summary = "Eliminar usuario", description = "Borra un usuario por ID. Solo accesible por **Profesor**.")
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
    
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene la lista completa de usuarios. Solo accesible por **Profesor**.")
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
    
    @Operation(summary = "Obtener perfil por ID", description = "Recupera la información detallada de un usuario.")
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
    
    @Operation(summary = "Enviar código de activación", description = "Genera un código aleatorio y lo envía al correo del usuario para validar su cuenta.")
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
    
    @Operation(
        summary = "Confirmar código de activación",
        description = "Valida el código enviado al correo y activa la cuenta del usuario.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = @ExampleObject(value = "{\"correo\": \"entrenador@gmail.com\", \"codigo\": \"123456\"}"))
        )
    )
    
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
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper = 
                    new org.springframework.mail.javamail.MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(correo);
            helper.setSubject("¡Bienvenido Entrenador! - PokeAPI - F&F");

            String urlAventura = "http://192.167.0.79:4200/";

            // Estilo HTML embebido directamente
            String htmlBody = "<div style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">"
                    + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px; background-color: #ffffff; border-radius: 8px; overflow: hidden;\">"
                    + "<tr><td bgcolor=\"#ef5350\" style=\"padding: 25px; text-align: center;\"><h1 style=\"color: white; margin: 0; text-transform: uppercase;\">¡Tu aventura comienza!</h1></td></tr>"
                    + "<tr><td bgcolor=\"#263238\" style=\"padding: 4px;\"></td></tr>"
                    + "<tr><td style=\"padding: 30px;\">"
                    + "<p style=\"font-size: 16px; color: #333;\">¡Hola!<br><br>Tu cuenta en <strong>PokeAPI - F&F</strong> se ha creado con éxito. Estamos felices de tenerte en nuestra comunidad de entrenadores.</p>"
                    + "<div style=\"text-align: center; margin: 30px 0;\">"
                    + "<a href=\"" + urlAventura + "\" style=\"background-color: #ef5350; color: white; padding: 12px 25px; text-decoration: none; font-weight: bold; border-radius: 25px; border: 2px solid #263238; display: inline-block; text-transform: uppercase;\">Iniciar Aventura</a>"
                    + "</div>"
                    + "</td></tr>"
                    + "</table></div>";

            helper.setText(htmlBody, true); // El 'true' activa el renderizado de HTML

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
    
    @PostMapping("/enviar-enlace-validacion/{correo}")
    public ResponseEntity<Result> enviarEnlace(@PathVariable String correo) {
        Result result = new Result();
        try {
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper = 
                    new org.springframework.mail.javamail.MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(correo);
            helper.setSubject("Activa tu cuenta de Entrenador - PokeAPI");

            String enlace = "http://192.167.0.65:8081/api/usuario/activar-cuenta/" + correo;

            String htmlBody = "<div style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">"
                    + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px; background-color: #ffffff; border-radius: 8px; overflow: hidden;\">"
                    + "<tr><td bgcolor=\"#ef5350\" style=\"padding: 25px; text-align: center;\"><h1 style=\"color: white; margin: 0; text-transform: uppercase;\">¡Verificación de Cuenta!</h1></td></tr>"
                    + "<tr><td bgcolor=\"#263238\" style=\"padding: 4px;\"></td></tr>"
                    + "<tr><td style=\"padding: 30px;\">"
                    + "<p style=\"font-size: 16px; color: #333;\">¡Hola!<br><br>Para poder capturar tus Pokémon y registrar tus batallas en la Pokédex, primero debes validar tu identidad de Entrenador.</p>"
                    + "<div style=\"text-align: center; margin: 30px 0;\">"
                    + "<a href=\"" + enlace + "\" style=\"background-color: #4caf50; color: white; padding: 12px 25px; text-decoration: none; font-weight: bold; border-radius: 25px; border: 2px solid #263238; display: inline-block; text-transform: uppercase;\">Activar mi Cuenta</a>"
                    + "</div>"
                    + "<p style=\"font-size: 12px; color: #777;\">Este enlace expira pronto. No lo compartas con el Equipo Rocket.</p>"
                    + "</td></tr>"
                    + "</table></div>";

            helper.setText(htmlBody, true);

            mailSender.send(message);
            result.correct = true;
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

    @PostMapping("/enviar-validacionPASS/{correo}")
    public ResponseEntity<Result> enviarCodigoPASS(@PathVariable String correo) {

        Result result = new Result();
        try {

            String codigo = String.valueOf((int) (Math.random() * 900000) + 100000);
            memoryCodes.put(correo, codigo);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("Validación de cuenta - PokeAPI");
            message.setText("¡Hola! Tu código de confirmación para cambiar tu contraseña es: " + codigo
                    + "\nPor favor, ingresalo en la pagina para actualizar tu contraseña.");

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
    
    @Operation(summary = "Cambiar estatus de cuenta", description = "Activa o desactiva a un usuario y envía una notificación por correo.")
    @PutMapping("/cambiar-estatus")
    public ResponseEntity<Result> cambiarEstatus(@RequestBody Map<String, Object> datos) {
        Result result = new Result();
        try {
            String correo = (String) datos.get("correo");
            boolean activar = (boolean) datos.get("estatus");

            if (activar) {
                result = usuarioDAOJPAImplementation.ActivacionUsuario(correo);
            } else {
                result = usuarioDAOJPAImplementation.DesactivacionUsuario(correo);
            }

            if (result.correct) {
                enviarCorreoEstatus(correo, activar);
                result.object = "Estado actualizado y notificación enviada.";
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error en el cambio de estatus: " + ex.getMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

    private void enviarCorreoEstatus(String correo, boolean fueActivado) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(correo);

        if (fueActivado) {
            message.setSubject("¡Tu cuenta ha sido Reactivada! - PokeAPI");
            message.setText("¡Hola de nuevo!\n\nTu cuenta de Entrenador en PokeAPI ha sido activada correctamente. "
                    + "Ya puedes iniciar sesión y continuar tu aventura, http://192.167.0.79:4200/ ");
        } else {
            message.setSubject("Notificación de cuenta Desactivada - PokeAPI");
            message.setText("Hola.\n\nTe informamos que tu cuenta en PokeAPI ha sido desactivada temporalmente por un administrador. "
                    + "Si crees que esto es un error, por favor contáctanos.");
        }

        mailSender.send(message);
    }
    
    @Operation(summary = "Cambiar contraseña directo", description = "Actualiza la contraseña de un usuario mediante correo y nueva clave.")
    @PutMapping("/reset-password")
    @PreAuthorize("hasAnyRole('Profesor', 'Maestro', 'Entrenador')")
    public ResponseEntity<Result> resetPassword(@RequestBody Map<String, String> payload) {
        String correo = payload.get("correo");
        String password = payload.get("password");

        Result result = usuarioDAOJPAImplementation.cambiarPasswordDirecto(correo, password);
        return ResponseEntity.ok(result);
    }
    
    @Operation(summary = "Validación vía enlace", description = "Activa la cuenta al hacer clic en el botón del correo.")
    @GetMapping("/activar-cuenta/{correo}")
    public ResponseEntity<Void> activarViaEnlace(@PathVariable String correo) {
        usuarioDAOJPAImplementation.ActivacionUsuario(correo);


        return ResponseEntity.status(HttpStatus.FOUND)
                             .location(URI.create("http://192.167.0.79:4200/activacion-exitosa"))
                             .build();
    }

}
