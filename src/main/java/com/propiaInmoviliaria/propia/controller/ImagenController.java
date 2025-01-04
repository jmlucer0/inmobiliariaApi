package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.service.ImagenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
@Tag(name = "Images", description = "Endpoints for managing property images.")
public class ImagenController {

    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    @Operation(
            summary = "Upload images",
            description = "Uploads one or more images to a specific property by its ID. Images are stored in a bucket, and the corresponding URLs are saved in the database.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the property to associate the uploaded images with.",
                            example = "42"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "A list of image files to upload. Max = 5",
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(type = "array", format = "binary", description = "Image files.")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Images uploaded successfully. Returns a message with the details.",
                            content = @Content(
                                    mediaType = "text/plain"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request. One or more files are empty."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error."
                    )
            }
    )
    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadImage(@RequestParam("files") List<MultipartFile> files, @PathVariable Long id) throws IOException {
        if (files.stream().anyMatch(file -> file.isEmpty())) {
            return ResponseEntity.badRequest().body("Uno o más archivos están vacíos.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(imagenService.subirImagen(files, id));
    }

    @Operation(
            summary = "Delete an image",
            description = "Deletes an image from a specific property by its ID and the image key.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the property associated with the image.",
                            example = "42"
                    ),
                    @Parameter(
                            name = "key",
                            description = "The key of the image to delete from the storage bucket.",
                            example = "property42/image123.jpg"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Image deleted successfully."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Image or property not found."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error."
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id, @RequestParam String key){
        imagenService.eliminarImagenDePropiedad(key, id);
        return ResponseEntity.noContent().build();

    }
}
