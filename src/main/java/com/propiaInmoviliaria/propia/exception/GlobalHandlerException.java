package com.propiaInmoviliaria.propia.exception;


import com.propiaInmoviliaria.propia.exception.imagenException.ImagenDeletionException;
import com.propiaInmoviliaria.propia.exception.imagenException.ImagenExtentionException;
import com.propiaInmoviliaria.propia.exception.imagenException.ImagenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException extends RuntimeException{

    @ExceptionHandler(ImagenNotFoundException.class)
    public ResponseEntity<String> handlerImageNotFountException(ImagenNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(ImagenDeletionException.class)
    public ResponseEntity<String> hadlerImagenDeletionException(ImagenDeletionException exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler(ImagenExtentionException.class)
    public ResponseEntity<String> handlerImagenExtencionException(ImagenExtentionException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }


}
