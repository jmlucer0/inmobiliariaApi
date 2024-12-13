package com.propiaInmoviliaria.propia.mapper;

import com.propiaInmoviliaria.propia.dtos.propiedad.ActualizarPropiedadDto;
import com.propiaInmoviliaria.propia.dtos.propiedad.CrearPropiedadDto;
import com.propiaInmoviliaria.propia.dtos.propiedad.PropiedadDto;
import com.propiaInmoviliaria.propia.model.Propiedad;
import org.springframework.stereotype.Component;

@Component
public class PropiedadMapper {

    private final DireccionMapper direccionMapper;

    public PropiedadMapper(DireccionMapper direccionMapper) {
        this.direccionMapper = direccionMapper;
    }


    public PropiedadDto propiedadDto(Propiedad propiedad){
        PropiedadDto propiedadDto = new PropiedadDto();

        propiedadDto.setSuperficieTotal(propiedad.getSuperficieTotal());
        propiedadDto.setTipoDePropiedad(propiedad.getTipoDePropiedad());
        propiedadDto.setCochera(propiedad.getCochera());
        propiedadDto.setPatio(propiedad.getPatio());
        propiedadDto.setPrecio(propiedad.getPrecio());
        propiedadDto.setImagenes(propiedad.getImagenes());
        propiedadDto.setDisponibilidad(propiedad.getDisponibilidad());
        propiedadDto.setBanios(propiedad.getBanios());
        propiedadDto.setDormitorios(propiedad.getDormitorios());
        propiedadDto.setSuperficieCubierta(propiedad.getSuperficieCubierta());
        propiedadDto.setTipoDeOperacion(propiedad.getTipoDeOperacion());
        propiedadDto.setObservaciones(propiedad.getObsevaciones());

        if (propiedad.getPropietario() != null){
            propiedadDto.setPropietarioId(propiedad.getPropietario().getId());
        }
        propiedadDto.setDireccion(propiedad.getDireccion());
        if (propiedad.getDireccion() != null) {
            propiedadDto.setDireccion(direccionMapper.toEntity(propiedad.getDireccion()));
        }

        return propiedadDto;
    }
    public Propiedad toEntity(PropiedadDto propiedadDto) {
        Propiedad propiedad = new Propiedad();


        propiedad.setSuperficieTotal(propiedadDto.getSuperficieTotal());
        propiedad.setPrecio(propiedadDto.getPrecio());
        propiedad.setImagenes(propiedadDto.getImagenes());
        propiedad.setTipoDePropiedad(propiedadDto.getTipoDePropiedad());
        propiedad.setDisponibilidad(propiedadDto.getDisponibilidad());
        propiedad.setCochera(propiedadDto.getCochera());
        propiedad.setPatio(propiedadDto.getPatio());
        propiedad.setTipoDeOperacion(propiedadDto.getTipoDeOperacion());
        propiedad.setBanios(propiedadDto.getBanios());
        propiedad.setDormitorios(propiedadDto.getDormitorios());
        propiedad.setSuperficieCubierta(propiedadDto.getSuperficieCubierta());
        propiedad.setObsevaciones(propiedadDto.getObservaciones());

        if (propiedadDto.getDireccion() != null) {
            propiedad.setDireccion(direccionMapper.toEntity(propiedadDto.getDireccion()));
        }

        return propiedad;
    }

    public Propiedad toEntityFromCrearPropiedad(CrearPropiedadDto propiedadDto){
        Propiedad propiedad = new Propiedad();

        propiedad.setSuperficieTotal(propiedadDto.getSuperficieTotal());
        propiedad.setPrecio(propiedadDto.getPrecio());
        propiedad.setImagenes(propiedadDto.getImagenes());
        propiedad.setTipoDePropiedad(propiedadDto.getTipoDePropiedad());
        propiedad.setDisponibilidad(propiedadDto.getDisponibilidad());
        propiedad.setCochera(propiedadDto.getCochera());
        propiedad.setPatio(propiedadDto.getPatio());
        propiedad.setTipoDeOperacion(propiedadDto.getTipoDeOperacion());
        propiedad.setBanios(propiedadDto.getBanios());
        propiedad.setDormitorios(propiedadDto.getDormitorios());
        propiedad.setSuperficieCubierta(propiedadDto.getSuperficieCubierta());
        propiedad.setObsevaciones(propiedadDto.getObservaciones());

        if (propiedadDto.getDireccion() != null) {
            propiedad.setDireccion(direccionMapper.toEntity(propiedadDto.getDireccion()));
        }

        return propiedad;
    }

//    public Propiedad formActuializarToEntity(ActualizarPropiedadDto propiedadDto){
//        Propiedad propiedad = new Propiedad();
//
//
//    }
}
