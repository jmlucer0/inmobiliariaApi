package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.assembler.PropiedadFiltroModelAssembler;
import com.propiaInmoviliaria.propia.assembler.PropiedadModelAssembler;
import com.propiaInmoviliaria.propia.dtos.propiedad.ActualizarPropiedadDto;
import com.propiaInmoviliaria.propia.dtos.propiedad.CrearPropiedadDto;
import com.propiaInmoviliaria.propia.dtos.propiedad.FiltroPropiedadDto;
import com.propiaInmoviliaria.propia.dtos.propiedad.PropiedadDto;
import com.propiaInmoviliaria.propia.enums.Disponibilidad;
import com.propiaInmoviliaria.propia.enums.TipoDeOperacion;
import com.propiaInmoviliaria.propia.enums.TipoDePropiedad;
import com.propiaInmoviliaria.propia.mapper.PropiedadMapper;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.service.PropiedadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/propiedad")
@Tag(name = "Real Estate Property Controller", description = "Manages real estate properties")
public class PropiedadController {

    private final PropiedadService propiedadService;
    private final PropiedadMapper mapper;
    private final PropiedadModelAssembler modelAssembler;
    private final PropiedadFiltroModelAssembler filtroModelAssembler;

    public PropiedadController(PropiedadService propiedadService, PropiedadMapper mapper, PropiedadModelAssembler modelAssembler, PropiedadFiltroModelAssembler filtroModelAssembler) {
        this.propiedadService = propiedadService;
        this.mapper = mapper;
        this.modelAssembler = modelAssembler;
        this.filtroModelAssembler = filtroModelAssembler;
    }

    @Operation(
            summary = "Register a new real estate property",
            tags = {"Real Estate Property Management"},
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
    public ResponseEntity<EntityModel<PropiedadDto>> registrarPropiedad(@RequestBody CrearPropiedadDto propiedadDto){
        Propiedad nuevaPropiedad = propiedadService.savePropiedad(propiedadDto);
        PropiedadDto datosPropiedad = mapper.propiedadDto(nuevaPropiedad);
        return ResponseEntity.ok(modelAssembler.toModel(datosPropiedad));
    }

    @GetMapping("/list")
    @Operation(
            summary = "Get List of all Real Estate Property",
            description = "Returns a paginated list of Real Estate Property. If no pagination is provided, the default page size is 10.",
            tags = {"Real Estate Property Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A paginated list of Real Estate Property.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "",
                    content = @Content(mediaType = "text/plain")
            )
    })
    public ResponseEntity<PagedModel<EntityModel<PropiedadDto>>> listarPropiedades(@Parameter(hidden = true)Pageable pageable){
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(10);
        }
        Page<PropiedadDto> propiedadPage = propiedadService.listarPropiedades(pageable);
        List<EntityModel<PropiedadDto>> entityModels = propiedadPage.stream()
                .map(propiedadDto -> modelAssembler.toModel(propiedadDto)).collect(Collectors.toList());
        PagedModel<EntityModel<PropiedadDto>> propiedadPageModel = PagedModel.of(entityModels,
                new PagedModel.PageMetadata(propiedadPage.getSize(), propiedadPage.getNumber(), propiedadPage.getTotalElements()));
        return ResponseEntity.ok(propiedadPageModel);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<EntityModel<FiltroPropiedadDto>>> filtrarPropiedades(
            @Parameter(hidden = true)Pageable pageable,
            @RequestParam(required = false) String numeroDeReferencia,
            @RequestParam(required = false) Long precioMin,
            @RequestParam(required = false) Long precioMax,
            @RequestParam(required = false) TipoDeOperacion tipoDeOperacion,
            @RequestParam(required = false) TipoDePropiedad tipoDePropiedad,
            @RequestParam(required = false) Disponibilidad disponibilidad,
            @RequestParam(required = false) Boolean cochera,
            @RequestParam(required = false) Boolean patio,
            @RequestParam(required = false) Integer banios,
            @RequestParam(required = false) Integer dormitorios,
            @RequestParam(required = false) String direccion
            ){
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(10);
        }
        Page<Propiedad> propiedadPage = propiedadService.buscarConFiltros(
                pageable,
                numeroDeReferencia,
                precioMin,
                precioMax,
                tipoDeOperacion,
                tipoDePropiedad,
                disponibilidad,
                cochera,
                patio,
                banios,
                dormitorios,
                direccion
        );
        List<EntityModel<FiltroPropiedadDto>> entityModels = propiedadPage.stream()
                .map(propiedad -> {
                    FiltroPropiedadDto dto = mapper.convertirAFiltroPropiedadDto(propiedad);
                    return filtroModelAssembler.toModel(dto);
                })
                .collect(Collectors.toList());
        PagedModel<EntityModel<FiltroPropiedadDto>> pagedModel = PagedModel.of(
                entityModels,
                new PagedModel.PageMetadata(
                        propiedadPage.getSize(),
                        propiedadPage.getNumber(),
                        propiedadPage.getTotalElements(),
                        propiedadPage.getTotalPages()
                )
        );

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Real Estate Property by ID",
            description = "Returns the details of a Real Estate Property based on the provided ID.",
            tags = {"Real Estate Property Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Real Estate Property found and returned.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EntityModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Real Estate Property not found.",
                    content = @Content(mediaType = "text/plain")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(mediaType = "text/plain")
            )
    })
    public ResponseEntity<EntityModel<PropiedadDto>> buscarPropiedadPorId(@PathVariable Long id){
        Propiedad propiedad = propiedadService.buscarPorId(id);
        PropiedadDto datosPropiedad = mapper.propiedadDto(propiedad);
        return ResponseEntity.ok(modelAssembler.toModel(datosPropiedad));
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Update Real Estate Property",
            description = "Updates an existing Real Estate Property with the provided ID and new data. Returns the updated Real Estate Property details.",
            tags = {"Real Estate Property Management"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated Real Estate Property details.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PropiedadDto.class)
                    )
            )
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Real Estate Property updated successfully.",
                    content = @Content(
                            schema = @Schema(implementation = EntityModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Real Estate Property data.",
                    content = @Content(mediaType = "text/plain")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Real Estate Property not found.",
                    content = @Content(mediaType = "text/plain")
            )
    }
    )
    public ResponseEntity<EntityModel<PropiedadDto>> actualizarPropiedad(@RequestParam Long id, @RequestBody ActualizarPropiedadDto propiedadDto){
        Propiedad propiedad = propiedadService.actualizarPropiedad(id, propiedadDto);
        PropiedadDto datosPropiedad = mapper.propiedadDto(propiedad);
        return ResponseEntity.ok(modelAssembler.toModel(datosPropiedad));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Delete Real Estate Property",
            description = "Deletes a Real Estate Property from the system based on the provided ID.",
            tags = {"Real Estate Property Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Real Estate Property successfully deleted.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Real Estate Property not found.",
                    content = @Content
            )
    })
    public ResponseEntity eliminarPropiedad(@PathVariable Long id){
        boolean borrado = propiedadService.borrarPropiedad(id);
        if (borrado){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
