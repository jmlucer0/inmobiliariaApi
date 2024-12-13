package com.propiaInmoviliaria.propia.util.updater;

import com.propiaInmoviliaria.propia.dtos.propiedad.ActualizarPropiedadDto;
import com.propiaInmoviliaria.propia.model.Propietario;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.util.FieldUpdater;
import org.springframework.stereotype.Component;

@Component
public class PropiedadUpdater {

    private final FieldUpdater fieldUpdater;

    public PropiedadUpdater(FieldUpdater fieldUpdater) {
        this.fieldUpdater = fieldUpdater;
    }

    public boolean actualizarPropiedad(Propiedad propiedadExistente, ActualizarPropiedadDto propiedadDto, Propietario propietario){
        boolean actualizado = false;

        actualizado |= fieldUpdater.actualizarCampo(() -> propiedadExistente.getPropietario().getId(), id -> propiedadExistente.setPropietario(propietario), propiedadDto.getPropietarioId());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getCochera, propiedadExistente::setCochera, propiedadDto.getCochera());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getPatio, propiedadExistente::setPatio, propiedadDto.getPatio());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getSuperficieTotal, propiedadExistente::setSuperficieTotal, propiedadDto.getSuperficieTotal());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getSuperficieCubierta, propiedadExistente::setSuperficieCubierta, propiedadDto.getSuperficieCubierta());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getPrecio, propiedadExistente::setPrecio, propiedadDto.getPrecio());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getTipoDePropiedad, propiedadExistente::setTipoDePropiedad, propiedadDto.getTipoDePropiedad());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getTipoDeOperacion, propiedadExistente::setTipoDeOperacion, propiedadDto.getTipoDeOperacion());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getDisponibilidad, propiedadExistente::setDisponibilidad, propiedadDto.getDisponibilidad());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getBanios, propiedadExistente::setBanios, propiedadDto.getBanios());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getDormitorios, propiedadExistente::setDormitorios, propiedadDto.getDormitorios());
        actualizado |= fieldUpdater.actualizarCampo(propiedadExistente::getObsevaciones, propiedadExistente::setObsevaciones, propiedadDto.getObservaciones());

        return actualizado;

    }

}
