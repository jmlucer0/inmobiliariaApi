package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.dtos.PropiedadDto;
import com.propiaInmoviliaria.propia.mapper.PropiedadMapper;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.service.PropiedadService;
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
public class PropiedadController {

    private final PropiedadService propiedadService;
    private final PropiedadMapper mapper;

    public PropiedadController(PropiedadService propiedadService, PropiedadMapper mapper) {
        this.propiedadService = propiedadService;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<PropiedadDto>> registrarPropiedad(@RequestBody PropiedadDto propiedadDto){
        Propiedad nuevaPropiedad = propiedadService.savePropiedad(propiedadDto);
        PropiedadDto datosPropiedad = mapper.propiedadDto(nuevaPropiedad);

        EntityModel<PropiedadDto> propiedadModel = EntityModel.of(datosPropiedad,
                linkTo(methodOn(PropiedadController.class).buscarPropiedadPorId(datosPropiedad.getId())).withSelfRel(),
                linkTo(methodOn(PropiedadController.class).actualizarPropiedad(datosPropiedad.getId(), null)).withRel("update"),
                linkTo(methodOn(PropiedadController.class).eliminarPropiedad(datosPropiedad.getId())).withRel("delete"),
                linkTo(methodOn(PropiedadController.class).listarPropiedades(null)).withRel("propiedades")
                );
        return ResponseEntity.ok(propiedadModel);
    }

    @GetMapping("/list")
    public ResponseEntity<PagedModel<EntityModel<PropiedadDto>>> listarPropiedades(Pageable pageable){
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        Page<PropiedadDto> propiedadPage = propiedadService.listarPropiedades(pageable);
        List<EntityModel<PropiedadDto>> entityModels = propiedadPage.stream()
                .map(propiedadDto -> EntityModel.of(propiedadDto,
                        linkTo(methodOn(PropiedadController.class).buscarPropiedadPorId(propiedadDto.getId())).withSelfRel(),
                        linkTo(methodOn(PropiedadController.class).actualizarPropiedad(propiedadDto.getId(), null)).withRel("update"),
                        linkTo(methodOn(PropiedadController.class).eliminarPropiedad(propiedadDto.getId())).withRel("delete")))
                .collect(Collectors.toList());
        PagedModel<EntityModel<PropiedadDto>> propiedadPageModel = PagedModel.of(entityModels,
                new PagedModel.PageMetadata(propiedadPage.getSize(), propiedadPage.getNumber(), propiedadPage.getTotalElements()));
        return ResponseEntity.ok(propiedadPageModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PropiedadDto>> buscarPropiedadPorId(@PathVariable Long id){
        Propiedad propiedad = propiedadService.buscarPorId(id);
        PropiedadDto datosPropiedad = mapper.propiedadDto(propiedad);

        EntityModel<PropiedadDto> propiedadModel = EntityModel.of(datosPropiedad,
                linkTo(methodOn(PropiedadController.class).buscarPropiedadPorId(datosPropiedad.getId())).withSelfRel(),
                linkTo(methodOn(PropiedadController.class).actualizarPropiedad(id, datosPropiedad)).withRel("update"),
                linkTo(methodOn(PropiedadController.class).eliminarPropiedad(datosPropiedad.getId())).withRel("delete"),
                linkTo(methodOn(PropiedadController.class).listarPropiedades(null)).withRel("propiedades"));
        return ResponseEntity.ok(propiedadModel);
    }

    @PostMapping("/{id}")
    public ResponseEntity<EntityModel<PropiedadDto>> actualizarPropiedad(@RequestParam Long id, @RequestBody PropiedadDto propiedadDto){
        Propiedad propiedad = propiedadService.actualizarPropiedad(id, propiedadDto);
        PropiedadDto propiedadActualizada = mapper.propiedadDto(propiedad);

        EntityModel<PropiedadDto> propiedadModel = EntityModel.of(propiedadActualizada,
                linkTo(methodOn(PropiedadController.class).buscarPropiedadPorId(propiedadActualizada.getId())).withSelfRel(),
                linkTo(methodOn(PropiedadController.class).actualizarPropiedad(id, propiedadActualizada)).withRel("update"),
                linkTo(methodOn(PropiedadController.class).eliminarPropiedad(propiedadActualizada.getId())).withRel("delete"),
                linkTo(methodOn(PropiedadController.class).listarPropiedades(null)).withRel("propiedades")
                );
        return ResponseEntity.ok(propiedadModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPropiedad(@PathVariable Long id){
        boolean borrado = propiedadService.borrarPropiedad(id);
        if (borrado){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
