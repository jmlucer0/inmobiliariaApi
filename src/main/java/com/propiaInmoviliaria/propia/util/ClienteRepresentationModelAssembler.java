package com.propiaInmoviliaria.propia.util;

import com.propiaInmoviliaria.propia.controller.ClienteController;
import com.propiaInmoviliaria.propia.dtos.ClienteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteRepresentationModelAssembler extends RepresentationModelAssemblerSupport<ClienteDto, EntityModel<ClienteDto>> {

    public ClienteRepresentationModelAssembler() {
        super(ClienteController.class, (Class<EntityModel<ClienteDto>>)(Class<?>) EntityModel.class);
    }


    public PagedModel<EntityModel<ClienteDto>> toPagedModel(Page<ClienteDto> clientesPage) {
        List<EntityModel<ClienteDto>> clienteModels = clientesPage.getContent().stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return PagedModel.of(
                clienteModels,
                new PagedModel.PageMetadata(
                        clientesPage.getSize(),
                        clientesPage.getNumber(),
                        clientesPage.getTotalElements(),
                        clientesPage.getTotalPages()
                )
        );
    }

    @Override
    public EntityModel<ClienteDto> toModel(ClienteDto clienteDto) {
        return EntityModel.of(clienteDto,
                linkTo(methodOn(ClienteController.class).searchClienteById(clienteDto.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).clienteList(PageRequest.of(0, 5))).withRel("clientes"));
    }

    public EntityModel<ClienteDto> toNotPagedModel(ClienteDto clienteDto) {
        return toModel(clienteDto);

    }
}