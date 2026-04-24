
package com.digis01.FCruzPokemonAPI.JPA;

import jakarta.persistence.Column;
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
    @Column(name = "IDUSUARIO")
    private int idusuario;
    @Column(name = "NOMBREUSUARIO")
    private String nombreusuario;
    @Column(name = "APELLIDOPATERNO")
    private String apellidopaterno;
    @Column(name = "APELLIDOMATERNO")
    private String apellidomaterno;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "CORREO")
    private String correo;
    @Column(name = "PASSWORD")
    private String Password;
    @ManyToOne
    @JoinColumn(name = "IDROL")
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
