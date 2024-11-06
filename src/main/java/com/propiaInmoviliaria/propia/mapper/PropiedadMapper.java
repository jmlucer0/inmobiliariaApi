package com.propiaInmoviliaria.propia.mapper;

import com.propiaInmoviliaria.propia.dtos.PropiedadDto;
import com.propiaInmoviliaria.propia.model.Direccion;
import com.propiaInmoviliaria.propia.model.Propiedad;
import org.springframework.stereotype.Component;

@Component
public class PropiedadMapper {

    public PropiedadDto propiedadDto(Propiedad propiedad){
        PropiedadDto propiedadDto = new PropiedadDto();

        propiedadDto.setId(propiedad.getId());
        propiedadDto.setSuperficie(propiedad.getSuperficie());
        propiedadDto.setTipoDePropiedad(propiedad.getTipoDePropiedad());
        propiedadDto.setCochera(propiedad.getCochera());
        propiedadDto.setPatio(propiedad.getPatio());
        propiedadDto.setPrecio(propiedad.getPrecio());
        propiedadDto.setImagenes(propiedad.getImagenes());
        propiedadDto.setDisponibilidad(propiedad.getDisponibilidad());

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


        propiedad.setSuperficie(propiedadDto.getSuperficie());
        propiedad.setPrecio(propiedadDto.getPrecio());
        propiedad.setImagenes(propiedadDto.getImagenes());
        propiedad.setTipoDePropiedad(propiedadDto.getTipoDePropiedad());
        propiedad.setDisponibilidad(propiedadDto.getDisponibilidad());
        propiedad.setCochera(propiedadDto.getCochera());
        propiedad.setPatio(propiedadDto.getPatio());

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
