package com.propiaInmoviliaria.propia.util.updater;

import com.propiaInmoviliaria.propia.dtos.propiedad.CrearPropiedadDto;
import com.propiaInmoviliaria.propia.model.Cliente;
import com.propiaInmoviliaria.propia.model.Propiedad;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class PropiedadUpdater {

    public boolean actualizarPropiedad(Propiedad propiedadExistente, CrearPropiedadDto propiedadDto, Cliente cliente){
        boolean actualizado = false;

        actualizado |= actualizarCampo(() -> propiedadExistente.getCliente().getId(), id -> propiedadExistente.setCliente(cliente), propiedadDto.getClienteId());
        actualizado |= actualizarCampo(propiedadExistente::getCochera, propiedadExistente::setCochera, propiedadDto.getCochera());
        actualizado |= actualizarCampo(propiedadExistente::getPatio, propiedadExistente::setPatio, propiedadDto.getPatio());
        actualizado |= actualizarCampo(propiedadExistente::getSuperficieTotal, propiedadExistente::setSuperficieTotal, propiedadDto.getSuperficieTotal());
        actualizado |= actualizarCampo(propiedadExistente::getSuperficieCubierta, propiedadExistente::setSuperficieCubierta, propiedadDto.getSuperficieCubierta());
        actualizado |= actualizarCampo(propiedadExistente::getPrecio, propiedadExistente::setPrecio, propiedadDto.getPrecio());
        actualizado |= actualizarCampo(propiedadExistente::getTipoDePropiedad, propiedadExistente::setTipoDePropiedad, propiedadDto.getTipoDePropiedad());
        actualizado |= actualizarCampo(propiedadExistente::getTipoDeOperacion, propiedadExistente::setTipoDeOperacion, propiedadDto.getTipoDeOperacion());
        actualizado |= actualizarCampo(propiedadExistente::getDisponibilidad, propiedadExistente::setDisponibilidad, propiedadDto.getDisponibilidad());
        actualizado |= actualizarCampo(propiedadExistente::getBanios, propiedadExistente::setBanios, propiedadDto.getBanios());
        actualizado |= actualizarCampo(propiedadExistente::getDormitorios, propiedadExistente::setDormitorios, propiedadDto.getDormitorios());
        actualizado |= actualizarCampo(propiedadExistente::getObsevaciones, propiedadExistente::setObsevaciones, propiedadDto.getObservaciones());

        return actualizado;

    }

    /*
    cuando se reforme Cliente hay que dejar de usar este metodo y hacer una inyeccion de dependecia a la clase
    FieldUpdater que tiene este metodo que se usa en todos los updaters
     */
    private <T> boolean actualizarCampo(Supplier<T> getter, Consumer<T> setter, T nuevoValor) {
        if (!Objects.equals(getter.get(), nuevoValor)) {
            setter.accept(nuevoValor);
            return true;
        }
        return false;
    }

}
