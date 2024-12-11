package com.propiaInmoviliaria.propia.assembler;

import com.propiaInmoviliaria.propia.controller.ClienteController;
import com.propiaInmoviliaria.propia.dtos.cliente.ClienteDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteDto, EntityModel<ClienteDto>> {
    @Override
    public EntityModel<ClienteDto> toModel(ClienteDto clienteDto) {
        return EntityModel.of(clienteDto,
                linkTo(methodOn(ClienteController.class).searchClienteById(clienteDto.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).updateCliente(clienteDto.getId(), clienteDto)).withRel("update"),
                linkTo(methodOn(ClienteController.class).deleteCliente(clienteDto.getId())).withRel("delete"),
                linkTo(methodOn(ClienteController.class).clienteList(null)).withRel("clientes")
                );
    }
}
