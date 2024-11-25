package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.assembler.ClienteModelAssembler;
import com.propiaInmoviliaria.propia.dtos.ClienteDto;
import com.propiaInmoviliaria.propia.mapper.ClienteMapper;
import com.propiaInmoviliaria.propia.model.Cliente;
import com.propiaInmoviliaria.propia.service.ClienteService;
import com.propiaInmoviliaria.propia.util.ClienteRepresentationModelAssembler;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cliente")
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

    @GetMapping
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

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDto>> searchClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.searchById(id);
        ClienteDto clienteDto = mapper.toDto(cliente);
        return ResponseEntity.ok(modelAssembler.toModel(clienteDto));
    }


    @PostMapping("/register")
    public ResponseEntity<EntityModel<ClienteDto>> registrarNuevoCliente(@RequestBody ClienteDto clienteDto) {
        Cliente cliente = clienteService.saveCliente(clienteDto);
        ClienteDto datosCliente = mapper.toDto(cliente);
        return ResponseEntity.ok(modelAssembler.toModel(datosCliente));
    }


    @PostMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDto>> updateCliente (@RequestParam Long id, @RequestBody ClienteDto clienteDto){
        Cliente cliente = clienteService.updateCliente(id, clienteDto);
        ClienteDto clienteUpdate = new ClienteDto(cliente);
      return ResponseEntity.ok(modelAssembler.toModel(clienteUpdate));
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
