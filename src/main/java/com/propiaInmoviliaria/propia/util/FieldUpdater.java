package com.propiaInmoviliaria.propia.util;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class FieldUpdater {

    public <T> boolean actualizarCampo(Supplier<T> getter, Consumer<T> setter, T nuevoValor) {
        if (!Objects.equals(getter.get(), nuevoValor)) {
            setter.accept(nuevoValor);
            return true;
        }
        return false;
    }
}
