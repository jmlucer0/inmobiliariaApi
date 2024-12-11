package com.propiaInmoviliaria.propia.util.updater;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FieldUpdater {

    /*cambiar a la funcion actualizar campo cuando actualize
    lo que es cliente para dejar de usar este metodo y usar un updater como en propiedad*/
    public static <T> void updateField (T field, Consumer<T> setter){
        if (field != null){
            setter.accept(field);
        }
    }

    public <T> boolean actualizarCampo(Supplier<T> getter, Consumer<T> setter, T nuevoValor) {
        if (!Objects.equals(getter.get(), nuevoValor)) {
            setter.accept(nuevoValor);
            return true;
        }
        return false;
    }
}
