package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.assembler.PropiedadModelAssembler;
import com.propiaInmoviliaria.propia.dtos.PropiedadDto;
import com.propiaInmoviliaria.propia.mapper.PropiedadMapper;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.service.PropiedadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/propiedad")
@Tag(name = "Real Estate Property Controller", description = "Manages real estate properties")
public class PropiedadController {

    private final PropiedadService propiedadService;
    private final PropiedadMapper mapper;
    private final PropiedadModelAssembler modelAssembler;

    public PropiedadController(PropiedadService propiedadService, PropiedadMapper mapper, PropiedadModelAssembler modelAssembler) {
        this.propiedadService = propiedadService;
        this.mapper = mapper;
        this.modelAssembler = modelAssembler;
    }

    @Operation(
            summary = "Register a new real estate property",
            tags = {"Propiedad Management"},
            description = "Creates a new real estate property with the provided details and returns the registered property information.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Required data to register a new real estate property.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PropiedadDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Property successfully registered",
                            content = @Content(
                                    schema = @Schema(implementation = EntityModel.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<EntityModel<PropiedadDto>> registrarPropiedad(@RequestBody PropiedadDto propiedadDto){
        Propiedad nuevaPropiedad = propiedadService.savePropiedad(propiedadDto);
        PropiedadDto datosPropiedad = mapper.propiedadDto(nuevaPropiedad);
        return ResponseEntity.ok(modelAssembler.toModel(datosPropiedad));
    }

    @GetMapping("/list")
    public ResponseEntity<PagedModel<EntityModel<PropiedadDto>>> listarPropiedades(@Parameter(hidden = true)Pageable pageable){
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        Page<PropiedadDto> propiedadPage = propiedadService.listarPropiedades(pageable);
        List<EntityModel<PropiedadDto>> entityModels = propiedadPage.stream()
                .map(propiedadDto -> modelAssembler.toModel(propiedadDto)).collect(Collectors.toList());
        PagedModel<EntityModel<PropiedadDto>> propiedadPageModel = PagedModel.of(entityModels,
                new PagedModel.PageMetadata(propiedadPage.getSize(), propiedadPage.getNumber(), propiedadPage.getTotalElements()));
        return ResponseEntity.ok(propiedadPageModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PropiedadDto>> buscarPropiedadPorId(@PathVariable Long id){
        Propiedad propiedad = propiedadService.buscarPorId(id);
        PropiedadDto datosPropiedad = mapper.propiedadDto(propiedad);
        return ResponseEntity.ok(modelAssembler.toModel(datosPropiedad));
    }

    @PostMapping("/{id}")
    public ResponseEntity<EntityModel<PropiedadDto>> actualizarPropiedad(@RequestParam Long id, @RequestBody PropiedadDto propiedadDto){
        Propiedad propiedad = propiedadService.actualizarPropiedad(id, propiedadDto);
        PropiedadDto datosPropiedad = mapper.propiedadDto(propiedad);
        return ResponseEntity.ok(modelAssembler.toModel(datosPropiedad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPropiedad(@PathVariable Long id){
        boolean borrado = propiedadService.borrarPropiedad(id);
        if (borrado){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/calle-numero-ciudad")
    public ResponseEntity<PagedModel<EntityModel<PropiedadDto>>> buscarPorCalleNumeroCiudad(
            @RequestParam(value = "calle", required = false) String calle,
            @RequestParam(value = "numero", required = false) String numero,
            @RequestParam(value = "ciudad", required = false) String ciudad,
            @Parameter(hidden = true)Pageable pageable){
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        if (calle == null && numero == null && ciudad == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Page<PropiedadDto> propiedades = propiedadService.buscarPorCalleYNumero(calle, numero, ciudad, pageable);

        List<EntityModel<PropiedadDto>> entityModels = propiedades.getContent().stream()
                .map(propiedadDto -> EntityModel.of(propiedadDto,
                        linkTo(methodOn(PropiedadController.class).buscarPropiedadPorId(propiedadDto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                propiedades.getSize(), propiedades.getNumber(), propiedades.getTotalElements());

        PagedModel<EntityModel<PropiedadDto>> propiedadPageModel = PagedModel.of(entityModels, pageMetadata);

        return ResponseEntity.ok(propiedadPageModel);
    }



}
