package com.carlousuga.prueba_crud.dto;

public class Mensaje {
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
