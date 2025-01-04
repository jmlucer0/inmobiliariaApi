package com.propiaInmoviliaria.propia.assembler;

import com.propiaInmoviliaria.propia.dtos.propiedad.FiltroPropiedadDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PropiedadFiltroModelAssembler implements RepresentationModelAssembler<FiltroPropiedadDto, EntityModel<FiltroPropiedadDto>> {
    @Override
    public EntityModel<FiltroPropiedadDto> toModel(FiltroPropiedadDto propiedadDto) {

        EntityModel<FiltroPropiedadDto> entityModel = EntityModel.of(propiedadDto);

        Link selfLink = Link.of("/search").withSelfRel();
        entityModel.add(selfLink);
        return entityModel;
    }
}
