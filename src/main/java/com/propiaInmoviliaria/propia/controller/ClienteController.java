package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.dtos.ClienteDto;
import com.propiaInmoviliaria.propia.mapper.ClienteMapper;
import com.propiaInmoviliaria.propia.model.Cliente;
import com.propiaInmoviliaria.propia.service.ClienteService;
import com.propiaInmoviliaria.propia.util.ClienteRepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteMapper mapper;
    private final ClienteService clienteService;
    private final ClienteRepresentationModelAssembler clienteAssembler;
    private final PagedResourcesAssembler<ClienteDto> pagedResourcesAssembler;

    public ClienteController(ClienteMapper mapper, ClienteService clienteService,
                             ClienteRepresentationModelAssembler clienteAssembler,
                             PagedResourcesAssembler<ClienteDto> pagedResourcesAssembler) {
        this.mapper = mapper;
        this.clienteService = clienteService;
        this.clienteAssembler = clienteAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ClienteDto>>> clienteList(@Parameter(hidden = true)Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        Page<ClienteDto> clientesPage = clienteService.findAllActiveCliente(pageable);
        PagedModel<EntityModel<ClienteDto>> pagedModel = pagedResourcesAssembler.toModel(clientesPage);
        return ResponseEntity.ok(pagedModel);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDto>> searchClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.searchById(id);
        ClienteDto clienteDto = mapper.toDto(cliente);

        EntityModel<ClienteDto> clienteModel = EntityModel.of(clienteDto,
                linkTo(methodOn(ClienteController.class).searchClienteById(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).updateCliente(id, clienteDto)).withRel("update"),
                linkTo(methodOn(ClienteController.class).deleteCliente(id)).withRel("delete"),
                linkTo(methodOn(ClienteController.class).clienteList(null)).withRel("clientes"));
        return ResponseEntity.ok(clienteModel);
    }


    @PostMapping("/register")
    public ResponseEntity<EntityModel<ClienteDto>> registrarNuevoCliente(@RequestBody ClienteDto clienteDto) {
        Cliente cliente = clienteService.saveCliente(clienteDto);
        ClienteDto datosCliente = mapper.toDto(cliente);

        EntityModel<ClienteDto> clienteModel = EntityModel.of(datosCliente,
                linkTo(methodOn(ClienteController.class).searchClienteById(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).updateCliente(cliente.getId(), null)).withRel("update"),
                linkTo(methodOn(ClienteController.class).deleteCliente(cliente.getId())).withRel("delete"),
                linkTo(methodOn(ClienteController.class).clienteList(null)).withRel("clientes"));

        return ResponseEntity.ok(clienteModel);
    }


    @PostMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDto>> updateCliente (@RequestParam Long id, @RequestBody ClienteDto clienteDto){
        Cliente cliente = clienteService.updateCliente(id, clienteDto);
        ClienteDto clienteUpdate = new ClienteDto(cliente);

        EntityModel<ClienteDto> clienteDtoModel = EntityModel.of(clienteUpdate,
                linkTo(methodOn(ClienteController.class).searchClienteById(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).updateCliente(id, clienteUpdate)).withRel("update"),
                linkTo(methodOn(ClienteController.class).clienteList(null)).withRel("clientes"));
        return ResponseEntity.ok(clienteDtoModel);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteCliente(@PathVariable Long id){
        boolean isDeleted = clienteService.deleteClienteById(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
