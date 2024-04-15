package com.pelayo.ambientlab.excepciones;

public class HTTPStatusException extends Exception{

    private int estatus;

    private static String obtenerMensaje(int estatus) {
        switch (estatus) {
            case 401:
                return "Acceso no autorizado";
            case 403:
                return "Acceso no permitdo";
            case 404:
                return "Recurso no encontrado";
            default:
                return "Codigo de error: "+estatus;
        }
    }

    public HTTPStatusException(int estatus) {
        super(HTTPStatusException.obtenerMensaje(estatus));
        this.estatus = estatus;
    }

    public HTTPStatusException(int estatus, String mensaje) {
        super(mensaje);
        this.estatus = estatus;
    }

    public int getEstatus() { return estatus; }
}
