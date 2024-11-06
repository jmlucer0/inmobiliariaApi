package com.propiaInmoviliaria.propia.util;

import java.util.function.Consumer;

public class FieldUpdater {

    public static <T> void updateField (T field, Consumer<T> setter){
        if (field != null){
            setter.accept(field);
        }
    }
}
