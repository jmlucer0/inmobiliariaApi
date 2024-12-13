package com.propiaInmoviliaria.propia.mapper;

import com.propiaInmoviliaria.propia.dtos.propietario.PropietarioDto;
import com.propiaInmoviliaria.propia.dtos.propietario.CrearPropietarioDto;
import com.propiaInmoviliaria.propia.model.Propietario;
import org.springframework.stereotype.Component;

@Component
public class PropietarioMapper {

    private final DireccionMapper direccionMapper;

    public PropietarioMapper(DireccionMapper direccionMapper) {
        this.direccionMapper = direccionMapper;
    }

    public PropietarioDto toDto(Propietario propietario) {
        PropietarioDto propietarioDto = new PropietarioDto();
        propietarioDto.setId(propietario.getId());
        propietarioDto.setNombre(propietario.getNombre());
        propietarioDto.setApellido(propietario.getApellido());
        propietarioDto.setTelefono(propietario.getTelefono());
        propietarioDto.setActive(propietario.getActive());

        if (propietario.getDireccion() != null) {
            propietarioDto.setDireccion(direccionMapper.toEntity(propietario.getDireccion()));
        }

        return propietarioDto;
    }

    public Propietario toEntity(PropietarioDto propietarioDto) {
        Propietario propietario = new Propietario();
        propietario.setId(propietarioDto.getId());
        propietario.setNombre(propietarioDto.getNombre());
        propietario.setApellido(propietarioDto.getApellido());
        propietario.setTelefono(propietarioDto.getTelefono());
        propietario.setActive(propietarioDto.getActive());

        if (propietarioDto.getDireccion() != null) {
            propietario.setDireccion(direccionMapper.toEntity(propietarioDto.getDireccion()));
        }

        return propietario;
    }

    public Propietario toEntityFromCrearPropietario(CrearPropietarioDto propietarioDto){
        Propietario propietario = new Propietario();
        propietario.setNombre(propietarioDto.getNombre());
        propietario.setApellido(propietarioDto.getApellido());
        propietario.setTelefono(propietarioDto.getTelefono());
        propietario.setEmail(propietarioDto.getEmail());

        if (propietarioDto.getDireccion() != null) {
            propietario.setDireccion(direccionMapper.toEntity(propietarioDto.getDireccion()));
        }

        return propietario;
    }
}
