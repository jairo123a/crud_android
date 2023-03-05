package com.example.gremlin.model;


import android.icu.text.DateFormat;

import java.sql.Time;
import java.util.Date;

public class Pedidos {

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }


    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    private String Uid;
    private String referencia;
    private String estado;
    private String cantidad;
    private String fecha;
    private String hora;

    public Pedidos() {

    }


    @Override
    public String toString() {
        return referencia + " " + fecha + " " + estado;
    }
}
