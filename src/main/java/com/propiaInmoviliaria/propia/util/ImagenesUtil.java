package com.propiaInmoviliaria.propia.util;

import com.propiaInmoviliaria.propia.exception.imagenException.ImagenDeletionException;
import com.propiaInmoviliaria.propia.exception.imagenException.ImagenExtentionException;
import com.propiaInmoviliaria.propia.exception.imagenException.ImagenNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ImagenesUtil {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final int MAX_FILES = 5;

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

        @Value("${aws.s3.region}")
    private String region;

    private static final Logger logger = LoggerFactory.getLogger(ImagenesUtil.class);

    public ImagenesUtil(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public List<String> uploadImage(List<MultipartFile> files, String propiedadId) throws IOException {

        if (files.size() > MAX_FILES) {
            throw new IllegalArgumentException("No se pueden subir más de 5 imágenes.");
        }


        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files){
            try {
                if (file.isEmpty()) {
                    throw new ImagenNotFoundException("Uno o más archivos están vacíos.");
                }
                if (file.getSize() > MAX_FILE_SIZE) {
                    throw new ImagenExtentionException("El archivo " + file.getOriginalFilename() + " excede el tamaño permitido de " + MAX_FILE_SIZE + " bytes.");
                }
                // Genera nombre único para el archivo
                String fileName = nombreDeImagen(file.getOriginalFilename(), propiedadId);

                // Construir la solicitud para S3
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build();

                // Subir el archivo a S3
                s3Client.putObject(
                        putObjectRequest,
                        RequestBody.fromInputStream(file.getInputStream(), file.getSize())
                );

                // Generar URL y agregarla a la lista
                String url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);

                urls.add(url);
            } catch (Exception e) {
                logger.error("Error al subir el archivo {}: {}", file.getOriginalFilename(), e.getMessage());
                throw new IOException("Error al subir el archivo", e);
            }
        }

        return urls;
    }

    public boolean borrarImagen(String key){
        try{
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(extraerKeyConUri(key))
                    .build());
            return true;
        }catch (Exception e){
            throw new ImagenDeletionException("no se pudo eliminar la imagen");
        }
    }

    private static String nombreDeImagen(String fileName, String propiedadId){
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String nuevoNombre = "images/"+ propiedadId + "/" + UUID.randomUUID().toString() + "_" + fecha + ImagenesUtil.formatoDeImagen(fileName);
        return nuevoNombre;
    }

    private static String formatoDeImagen(String fileName){
        if (fileName.isBlank() || fileName == null){
            throw new IllegalArgumentException("El nombre del archivo es nulo o inexistente");
        }
        int indicePunto = fileName.lastIndexOf(".");
        if (indicePunto == - 1 || indicePunto == fileName.length() - 1){
            throw new IllegalArgumentException("La extencion de archivo No es Valida: " + fileName);
        }
        String extencion = fileName.substring(indicePunto + 1).toLowerCase();
        if (!Set.of("jpg", "jpeg", "png").contains(extencion)){
            throw new IllegalArgumentException("La extencion del archivo no esta permitida: " + extencion);
        }
        return "." + extencion;
    }

    private static String extraerKeyConUri(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            if (path.startsWith("/")) {
                path = path.substring(1);
            }

            return path;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URL inválida: " + url, e);
        }
    }

}
