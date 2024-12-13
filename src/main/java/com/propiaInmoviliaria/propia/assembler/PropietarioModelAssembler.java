package com.propiaInmoviliaria.propia.assembler;

import com.propiaInmoviliaria.propia.controller.PropietarioController;
import com.propiaInmoviliaria.propia.dtos.propietario.PropietarioDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PropietarioModelAssembler implements RepresentationModelAssembler<PropietarioDto, EntityModel<PropietarioDto>> {
    @Override
    public EntityModel<PropietarioDto> toModel(PropietarioDto propietarioDto) {
        return EntityModel.of(propietarioDto,
                linkTo(methodOn(PropietarioController.class).searchClienteById(propietarioDto.getId())).withSelfRel(),
                linkTo(methodOn(PropietarioController.class).updatePropietario(propietarioDto.getId(), null)).withRel("update"),
                linkTo(methodOn(PropietarioController.class).deletePropietario(propietarioDto.getId())).withRel("delete"),
                linkTo(methodOn(PropietarioController.class).clienteList(null)).withRel("ownersList")
                );
    }

}
