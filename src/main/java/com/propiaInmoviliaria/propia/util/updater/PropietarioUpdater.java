package com.propiaInmoviliaria.propia.util.updater;

import com.propiaInmoviliaria.propia.dtos.propietario.ActualizarPropietarioDto;
import com.propiaInmoviliaria.propia.model.Propietario;
import com.propiaInmoviliaria.propia.util.FieldUpdater;
import org.springframework.stereotype.Component;

@Component
public class PropietarioUpdater {

    private final FieldUpdater fieldUpdater;


    public PropietarioUpdater(FieldUpdater fieldUpdater) {
        this.fieldUpdater = fieldUpdater;
    }


    public boolean actualizarPropietario(Propietario propietarioExistente, ActualizarPropietarioDto propietarioDto){
        boolean actualizado = false;
        actualizado |= fieldUpdater.actualizarCampo(propietarioExistente::getNombre, propietarioExistente::setNombre, propietarioDto.getNombre());
        actualizado |= fieldUpdater.actualizarCampo(propietarioExistente::getApellido, propietarioExistente::setApellido, propietarioDto.getApellido());
        actualizado |= fieldUpdater.actualizarCampo(propietarioExistente::getEmail, propietarioExistente::setEmail, propietarioDto.getEmail());
        actualizado |= fieldUpdater.actualizarCampo(propietarioExistente::getTelefono, propietarioExistente::setTelefono, propietarioDto.getTelefono());
        actualizado |= fieldUpdater.actualizarCampo(propietarioExistente::getActive, propietarioExistente::setActive, propietarioDto.getActive());
        return actualizado;
    }
}
