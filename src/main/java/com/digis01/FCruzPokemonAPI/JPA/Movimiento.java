package com.digis01.FCruzPokemonAPI.JPA;

public class Movimiento {

    private int idMovimiento;
    private String nombreMovimiento;
    private int poder;
    private int potencia;
    private int precisionMov;
    private int puntosPoder;
    private int ordenEjecucion;
    public Tipo tipo;
    private String efectos;

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getNombreMovimiento() {
        return nombreMovimiento;
    }

    public void setNombreMovimiento(String nombreMovimiento) {
        this.nombreMovimiento = nombreMovimiento;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public int getPrecisionMov() {
        return precisionMov;
    }

    public void setPrecisionMov(int precisionMov) {
        this.precisionMov = precisionMov;
    }

    public int getPuntosPoder() {
        return puntosPoder;
    }

    public void setPuntosPoder(int puntosPoder) {
        this.puntosPoder = puntosPoder;
    }

    public int getOrdenEjecucion() {
        return ordenEjecucion;
    }

    public void setOrdenEjecucion(int ordenEjecucion) {
        this.ordenEjecucion = ordenEjecucion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getEfectos() {
        return efectos;
    }

    public void setEfectos(String efectos) {
        this.efectos = efectos;
    }

}
