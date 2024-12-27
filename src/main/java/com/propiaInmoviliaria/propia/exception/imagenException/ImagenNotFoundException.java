package com.propiaInmoviliaria.propia.exception.imagenException;

public class ImagenNotFoundException extends RuntimeException{

    public ImagenNotFoundException(String mensaje){
        super(mensaje);
    }
}
