package com.propiaInmoviliaria.propia.mapper;

import com.propiaInmoviliaria.propia.model.Direccion;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper {

    public Direccion toEntity(Direccion nuevaDireccion){
        Direccion direccion = new Direccion();
        direccion.setId(nuevaDireccion.getId());
        direccion.setCalle(nuevaDireccion.getCalle());
        direccion.setNumero(nuevaDireccion.getNumero());
        direccion.setBarrio(nuevaDireccion.getBarrio());
        direccion.setLocalidad(nuevaDireccion.getLocalidad());
        return direccion;
    }
}
