
package com.digis01.FCruzPokemonAPI.JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idusuario;
    private String nombreusuario;
    private String apellidopaterno;
    private String apellidomaterno;
    private String userName;
    private String correo;
    private String Password;
    @ManyToOne
    @JoinColumn(name = "idrol")
    public Rol rol;
    
    public int getIdUsuario(){
        return idusuario;
    }
    
    public void setIdUsuario(int idusuario){
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombreusuario;
    }

    public void setNombre(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getApellidoPaterno() {
        return apellidopaterno;
    }

    public void setApellidoPaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidoMaterno() {
        return apellidomaterno;
    }

    public void setApellidoMaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    
    
}
