package com.propiaInmoviliaria.propia.mapper;

import com.propiaInmoviliaria.propia.dtos.propiedad.CrearPropiedadDto;
import com.propiaInmoviliaria.propia.dtos.propiedad.PropiedadDto;
import com.propiaInmoviliaria.propia.model.Direccion;
import com.propiaInmoviliaria.propia.model.Propiedad;
import org.springframework.stereotype.Component;

@Component
public class PropiedadMapper {

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

        if (propiedad.getCliente() != null){
            propiedadDto.setCliente(propiedad.getCliente().getId());
        }
        propiedadDto.setDireccion(propiedad.getDireccion());
        if (propiedad.getDireccion() != null) {
            Direccion direccion = new Direccion();
            direccion.setId(propiedad.getDireccion().getId());
            direccion.setStreet(propiedad.getDireccion().getStreet());
            direccion.setNumber(propiedad.getDireccion().getNumber());
            direccion.setCity(propiedad.getDireccion().getCity());
            propiedadDto.setDireccion(direccion);
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
            Direccion direccion = new Direccion();
            direccion.setStreet(propiedadDto.getDireccion().getStreet());
            direccion.setNumber(propiedadDto.getDireccion().getNumber());
            direccion.setCity(propiedadDto.getDireccion().getCity());
            propiedad.setDireccion(direccion);
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
            Direccion direccion = new Direccion();
            direccion.setStreet(propiedadDto.getDireccion().getStreet());
            direccion.setNumber(propiedadDto.getDireccion().getNumber());
            direccion.setCity(propiedadDto.getDireccion().getCity());
            propiedad.setDireccion(direccion);
        }

        return propiedad;
    }
}
