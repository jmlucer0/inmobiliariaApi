package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.assembler.PropietarioModelAssembler;
import com.propiaInmoviliaria.propia.dtos.propietario.ActualizarPropietarioDto;
import com.propiaInmoviliaria.propia.dtos.propietario.CrearPropietarioDto;
import com.propiaInmoviliaria.propia.dtos.propietario.PropietarioDto;
import com.propiaInmoviliaria.propia.mapper.PropietarioMapper;
import com.propiaInmoviliaria.propia.model.Propietario;
import com.propiaInmoviliaria.propia.service.PropietarioService;

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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/propietario")
@Tag(name = "Owner", description = "Controller for Owner")
public class PropietarioController {

    private final PropietarioMapper mapper;
    private final PropietarioService propietarioService;
    private final PropietarioModelAssembler modelAssembler;

    public PropietarioController(PropietarioMapper mapper, PropietarioService propietarioService,
                                 PagedResourcesAssembler<PropietarioDto> pagedResourcesAssembler, PropietarioModelAssembler modelAssembler) {
        this.mapper = mapper;
        this.propietarioService = propietarioService;
        this.modelAssembler = modelAssembler;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register Owner",
            description = "Resgister data for a new Owner",
            tags = {"Owner Management"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Required data to register a new Owner.",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PropietarioDto.class)
                    )
            )
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Owner registered successfully.",
                    content = @Content(
                            schema = @Schema(implementation = EntityModel.class)
                    )
            )
    }
    )
    public ResponseEntity<EntityModel<PropietarioDto>> registrarNuevoCliente(@RequestBody CrearPropietarioDto propietarioDto) {
        Propietario propietario = propietarioService.savePropietario(propietarioDto);
        PropietarioDto datosCliente = mapper.toDto(propietario);
        return ResponseEntity.ok(modelAssembler.toModel(datosCliente));
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Update Owner",
            description = "Updates an existing Owner with the provided ID and new data. Returns the updated Owner details.",
            tags = {"Owner Management"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated client details.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PropietarioDto.class)
                    )
            )
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Owner registered successfully.",
                    content = @Content(
                            schema = @Schema(implementation = EntityModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Owner data.",
                    content = @Content(mediaType = "text/plain")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Owner not found.",
                    content = @Content(mediaType = "text/plain")
            )
    }
    )
    public ResponseEntity<EntityModel<PropietarioDto>> updatePropietario(@RequestParam Long id, @RequestBody ActualizarPropietarioDto propietarioDto){
        Propietario propietario = propietarioService.updatePropietario(id, propietarioDto);
        PropietarioDto propietarioUpdate = mapper.toDto(propietario);
        return ResponseEntity.ok(modelAssembler.toModel(propietarioUpdate));
    }

    @Operation(
            summary = "Get Owner by ID",
            description = "Returns the details of a Owner based on the provided Owner ID.",
            tags = {"Client Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Owner found and returned.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EntityModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Owner not found.",
                    content = @Content(mediaType = "text/plain")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(mediaType = "text/plain")
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PropietarioDto>> searchClienteById(@PathVariable Long id) {
        Propietario propietario = propietarioService.searchById(id);
        PropietarioDto propietarioDto = mapper.toDto(propietario);
        return ResponseEntity.ok(modelAssembler.toModel(propietarioDto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Delete Owner",
            description = "Deletes a Owner from the system based on the provided ID.",
            tags = {"Owner Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Owner successfully deleted.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Owner not found.",
                    content = @Content
            )
    })
    public ResponseEntity deletePropietario(@PathVariable Long id){
        boolean isDeleted = propietarioService.deletePropietarioById(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(
            summary = "Get List of Active Owner",
            description = "Returns a paginated list of active Owner. If no pagination is provided, the default page size is 5.",
            tags = {"Owner Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A paginated list of active Owner.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters.",
                    content = @Content(mediaType = "text/plain")
            )
    })
    public ResponseEntity<PagedModel<EntityModel<PropietarioDto>>> clienteList(@Parameter(hidden = true)Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        Page<PropietarioDto> clientesPage = propietarioService.findAllActivePropietario(pageable);
        List<EntityModel<PropietarioDto>> entityModels = clientesPage.stream()
                .map(clienteDto -> modelAssembler.toModel(clienteDto)).collect(Collectors.toList());
        PagedModel<EntityModel<PropietarioDto>> clientePageModel = PagedModel.of(entityModels,
                new PagedModel.PageMetadata(clientesPage.getSize(), clientesPage.getNumber(), clientesPage.getTotalElements()));
        return ResponseEntity.ok(clientePageModel);

    }

}
