package com.propiaInmoviliaria.propia.service;

import com.propiaInmoviliaria.propia.exception.imagenException.ImagenDeletionException;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.repository.PropiedadRepository;
import com.propiaInmoviliaria.propia.util.ImagenesUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImagenService {

    private final PropiedadRepository propiedadRepository;

    private final ImagenesUtil imagenesUtil;


    public ImagenService(ImagenesUtil imagenesUtil, PropiedadRepository propiedadRepository) {
        this.imagenesUtil = imagenesUtil;
        this.propiedadRepository = propiedadRepository;
    }

    @Transactional
    public String subirImagen(List<MultipartFile> files, Long propiedadId) throws IOException {
        Propiedad propiedad = propiedadRepository.findById(propiedadId).orElseThrow(()-> new EntityNotFoundException("Id de propiedad invalido"));
        List<String> urlImagenes = imagenesUtil.uploadImage(files, String.valueOf(propiedadId));

        propiedad.setImagenes(urlImagenes);
        propiedadRepository.save(propiedad);
        return "imagenes cargadas";
    }

    public boolean eliminarImagenDePropiedad(String key, Long id){

        if (propiedadRepository.existsById(id)) {

            Propiedad propiedad = propiedadRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró la propiedad"));

            String path = propiedad.getImagenes().stream()
                    .filter(imagen -> imagen.equals(key))
                    .findFirst()
                    .orElseThrow(()-> new EntityNotFoundException("No se encontro la Propiedad con Id: " + id));

            if (!imagenesUtil.borrarImagen(path)) {
                throw new ImagenDeletionException("No se pudo eliminar la imagen del bucket: " + path);
            }

            propiedad.getImagenes().remove(key);
            propiedadRepository.save(propiedad);
            return true;
        }

        return false;
    }

}
