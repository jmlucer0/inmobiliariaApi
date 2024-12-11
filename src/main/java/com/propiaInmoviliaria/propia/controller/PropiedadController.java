package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.assembler.PropiedadModelAssembler;
import com.propiaInmoviliaria.propia.dtos.propiedad.CrearPropiedadDto;
import com.propiaInmoviliaria.propia.dtos.propiedad.PropiedadDto;
import com.propiaInmoviliaria.propia.mapper.PropiedadMapper;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.service.PropiedadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
    public ResponseEntity<EntityModel<PropiedadDto>> actualizarPropiedad(@RequestParam Long id, @RequestBody CrearPropiedadDto propiedadDto){
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

    @GetMapping("/calle-numero-ciudad")
    @Operation(
            summary = "Search Real Estate Property by Street, Number, and/or City",
            description = "Returns a paginated list of Real Estate Property filtered by street, number, and/or city.",
            tags = {"Real Estate Property Management"},
            parameters = {
                    @Parameter(
                            name = "calle",
                            description = "The street where the Real Estate Property is located.",
                            required = false,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string")
                    ),
                    @Parameter(
                            name = "numero",
                            description = "The number of the Real Estate Property street.",
                            required = false,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string")
                    ),
                    @Parameter(
                            name = "ciudad",
                            description = "The city where the Real Estate Property is located.",
                            required = false,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string")
                    ),
                    @Parameter(
                            name = "page",
                            description = "The page number to retrieve.",
                            required = false,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "integer", example = "0")
                    ),
                    @Parameter(
                            name = "size",
                            description = "The number of records per page.",
                            required = false,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "integer", example = "5")
                    ),
                    @Parameter(
                            name = "sort",
                            description = "The sorting criteria in the format `Real Estate Property (,asc|desc)`. Default is ascending.",
                            required = false,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", example = "calle,asc")
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paginated list of Real Estate Properties matching the filters.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PagedModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request. At least one filter must be provided.",
                    content = @Content(mediaType = "text/plain")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(mediaType = "text/plain")
            )
    })

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
