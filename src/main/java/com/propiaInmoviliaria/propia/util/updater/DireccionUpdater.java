package com.propiaInmoviliaria.propia.util.updater;

import com.propiaInmoviliaria.propia.model.Direccion;
import com.propiaInmoviliaria.propia.util.FieldUpdater;
import org.springframework.stereotype.Component;

@Component
public class DireccionUpdater {

    private final FieldUpdater fieldUpdater;

    public DireccionUpdater(FieldUpdater fieldUpdater) {
        this.fieldUpdater = fieldUpdater;
    }

    public boolean actualizarDireccion(Direccion direccionExistente, Direccion nuevaDireccion){
        boolean actualizado = false;
        actualizado |= fieldUpdater.actualizarCampo(direccionExistente::getId, nuevaDireccion::setId, nuevaDireccion.getId());
        actualizado |= fieldUpdater.actualizarCampo(direccionExistente::getCalle, nuevaDireccion::setCalle, nuevaDireccion.getCalle());
        actualizado |= fieldUpdater.actualizarCampo(direccionExistente::getNumero, nuevaDireccion::setNumero, nuevaDireccion.getNumero());
        actualizado |= fieldUpdater.actualizarCampo(direccionExistente::getBarrio, nuevaDireccion::setBarrio, nuevaDireccion.getBarrio());
        actualizado |= fieldUpdater.actualizarCampo(direccionExistente::getLocalidad, nuevaDireccion::setLocalidad, nuevaDireccion.getLocalidad());
        return actualizado;
    }
}
