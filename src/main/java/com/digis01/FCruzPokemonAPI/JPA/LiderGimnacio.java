package com.digis01.FCruzPokemonAPI.JPA;

public class LiderGimnacio {

    private int idLider;
    public Usuario usuario;
    public Gimnacio gimnacion;

    public int getIdLider() {
        return idLider;
    }

    public void setIdLider(int idLider) {
        this.idLider = idLider;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Gimnacio getGimnacion() {
        return gimnacion;
    }

    public void setGimnacion(Gimnacio gimnacion) {
        this.gimnacion = gimnacion;
    }

}
