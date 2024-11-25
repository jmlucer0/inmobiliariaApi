package com.propiaInmoviliaria.propia.assembler;

import com.propiaInmoviliaria.propia.controller.PropiedadController;
import com.propiaInmoviliaria.propia.dtos.PropiedadDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PropiedadModelAssembler implements RepresentationModelAssembler<PropiedadDto, EntityModel<PropiedadDto>> {
    @Override
    public EntityModel<PropiedadDto> toModel(PropiedadDto propiedadDto) {
        return EntityModel.of(propiedadDto,
                linkTo(methodOn(PropiedadController.class).buscarPropiedadPorId(propiedadDto.getId())).withSelfRel(),
                linkTo(methodOn(PropiedadController.class).actualizarPropiedad(propiedadDto.getId(), null)).withRel("update"),
                linkTo(methodOn(PropiedadController.class).eliminarPropiedad(propiedadDto.getId())).withRel("delete"),
                linkTo(methodOn(PropiedadController.class).listarPropiedades(null)).withRel("propiedades")
        );
    }

}
