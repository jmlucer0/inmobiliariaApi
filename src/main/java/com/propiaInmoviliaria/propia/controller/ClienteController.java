package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.assembler.ClienteModelAssembler;
import com.propiaInmoviliaria.propia.dtos.ClienteDto;
import com.propiaInmoviliaria.propia.mapper.ClienteMapper;
import com.propiaInmoviliaria.propia.model.Cliente;
import com.propiaInmoviliaria.propia.security.AuthUserRegister;
import com.propiaInmoviliaria.propia.service.ClienteService;
import com.propiaInmoviliaria.propia.util.ClienteRepresentationModelAssembler;

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
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Controller for Cliente")
public class ClienteController {

    private final ClienteMapper mapper;
    private final ClienteService clienteService;
    private final ClienteRepresentationModelAssembler clienteAssembler;
    private final ClienteModelAssembler modelAssembler;

    public ClienteController(ClienteMapper mapper, ClienteService clienteService,
                             ClienteRepresentationModelAssembler clienteAssembler,
                             PagedResourcesAssembler<ClienteDto> pagedResourcesAssembler, ClienteModelAssembler modelAssembler) {
        this.mapper = mapper;
        this.clienteService = clienteService;
        this.clienteAssembler = clienteAssembler;
        this.modelAssembler = modelAssembler;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register Client",
            description = "Resgister data for a new Client",
            tags = {"Cliente", "Register"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Required data to register a new Client.",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClienteDto.class)
                    )
            )
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Client registered successfully.",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
            )
    }
    )
    public ResponseEntity<EntityModel<ClienteDto>> registrarNuevoCliente(@RequestBody ClienteDto clienteDto) {
        Cliente cliente = clienteService.saveCliente(clienteDto);
        ClienteDto datosCliente = mapper.toDto(cliente);
        return ResponseEntity.ok(modelAssembler.toModel(datosCliente));
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Update Client",
            description = "Updates an existing client with the provided ID and new data. Returns the updated client details.",
            tags = {"Cliente Management", "Update"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated client details.",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClienteDto.class)
                    )
            )
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Client registered successfully.",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid client data.",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found.",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
            )
    }
    )
    public ResponseEntity<EntityModel<ClienteDto>> updateCliente (@RequestParam Long id, @RequestBody ClienteDto clienteDto){
        Cliente cliente = clienteService.updateCliente(id, clienteDto);
        ClienteDto clienteUpdate = new ClienteDto(cliente);
        return ResponseEntity.ok(modelAssembler.toModel(clienteUpdate));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Client by ID",
            description = "Returns the details of a client based on the provided client ID.",
            tags = {"Client Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Client found and returned.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found.",
                    content = @Content(mediaType = "text/plain")
            )
    })
    public ResponseEntity<EntityModel<ClienteDto>> searchClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.searchById(id);
        ClienteDto clienteDto = mapper.toDto(cliente);
        return ResponseEntity.ok(modelAssembler.toModel(clienteDto));
    }


    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Delete Client",
            description = "Deletes a client from the system based on the provided ID.",
            tags = {"Client Management", "Delete"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Client successfully deleted.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found.",
                    content = @Content
            )
    })
    public ResponseEntity deleteCliente(@PathVariable Long id){
        boolean isDeleted = clienteService.deleteClienteById(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(
            summary = "Get List of Active Clients",
            description = "Returns a paginated list of active clients. If no pagination is provided, the default page size is 5.",
            tags = {"Client Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A paginated list of active clients.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters.",
                    content = @Content(mediaType = "text/plain")
            )
    })
    public ResponseEntity<PagedModel<EntityModel<ClienteDto>>> clienteList(@Parameter(hidden = true)Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        Page<ClienteDto> clientesPage = clienteService.findAllActiveCliente(pageable);
        List<EntityModel<ClienteDto>> entityModels = clientesPage.stream()
                .map(clienteDto -> modelAssembler.toModel(clienteDto)).collect(Collectors.toList());
        PagedModel<EntityModel<ClienteDto>> clientePageModel = PagedModel.of(entityModels,
                new PagedModel.PageMetadata(clientesPage.getSize(), clientesPage.getNumber(), clientesPage.getTotalElements()));
        return ResponseEntity.ok(clientePageModel);

    }

}
