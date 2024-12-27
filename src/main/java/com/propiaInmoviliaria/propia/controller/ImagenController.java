package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.service.ImagenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImagenController {

    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadImage(@RequestParam("files") List<MultipartFile> files, @PathVariable Long id) throws IOException {
        if (files.stream().anyMatch(file -> file.isEmpty())) {
            return ResponseEntity.badRequest().body("Uno o más archivos están vacíos.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(imagenService.subirImagen(files, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id, @RequestParam String key){
        imagenService.eliminarImagenDePropiedad(key, id);
        return ResponseEntity.noContent().build();

    }
}
