package com.propiaInmoviliaria.propia.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.propiaInmoviliaria.propia.model.Direccion;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.util.Disponibilidad;
import com.propiaInmoviliaria.propia.util.TipoDePropiedad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropiedadDto {

    Long id;
    Long cliente;
    Direccion direccion;
    Double superficie;
    Long precio;
    List<String> imagenes;
    TipoDePropiedad tipoDePropiedad;
    Disponibilidad disponibilidad;
    Boolean cochera;
    Boolean patio;

    public PropiedadDto(Propiedad nuevaPropiedad) {

    }
}
