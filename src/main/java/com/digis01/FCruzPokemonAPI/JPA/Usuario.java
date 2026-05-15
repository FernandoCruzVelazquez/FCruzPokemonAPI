package com.digis01.FCruzPokemonAPI.JPA;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
@Schema(description = "Modelo que representa a un Entrenador en el sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autoincremental del usuario", example = "1")
    private int idusuario;

    @Schema(description = "Nombre(s) del usuario", example = "Ash")
    private String nombreusuario;

    @Schema(description = "Apellido paterno", example = "Ketchum")
    private String apellidopaterno;

    @Schema(description = "Apellido materno", example = "Satoshi")
    private String apellidomaterno;

    @Schema(description = "Nombre de usuario único para login", example = "ash_master")
    private String username;

    @Schema(description = "Correo electrónico institucional o personal", example = "ash@pueblopaleta.com")
    private String correo;

    @Schema(description = "Contraseña de acceso", example = "PikaPika123", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @Transient
    @Schema(description = "Campo temporal para validación de cambio de password", hidden = true)
    private String oldPassword;

    @Schema(description = "URL de la imagen de perfil o avatar", example = "https://midominio.com/perfil.png")
    private String imagen;

    @Schema(description = "Estado lógico del usuario (1: Activo, 0: Inactivo)", example = "1")
    private int estado;

    @Schema(description = "Estatus de activación por correo (1: Validado, 0: Pendiente)", example = "1")
    private int activacion;

    @ManyToOne
    @JoinColumn(name = "idrol")
    @Schema(description = "Rol asignado al usuario (Profesor, Maestro, Entrenador)")
    public Rol rol;

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getActivacion() {
        return activacion;
    }

    public void setActivacion(int activacion) {
        this.activacion = activacion;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    
    
}
