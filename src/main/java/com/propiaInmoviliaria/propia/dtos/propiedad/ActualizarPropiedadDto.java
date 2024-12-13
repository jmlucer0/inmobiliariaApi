package com.propiaInmoviliaria.propia.dtos.propiedad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.propiaInmoviliaria.propia.enums.Disponibilidad;
import com.propiaInmoviliaria.propia.enums.TipoDeOperacion;
import com.propiaInmoviliaria.propia.enums.TipoDePropiedad;
import com.propiaInmoviliaria.propia.model.Direccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualizarPropiedadDto {

    private Long propietarioId;
    private Direccion direccion;
    private Double superficieTotal;
    private Double superficieCubierta;
    private Long precio;
    private TipoDePropiedad tipoDePropiedad;
    private TipoDeOperacion tipoDeOperacion;
    private Disponibilidad disponibilidad;
    private Boolean cochera;
    private Boolean patio;
    private Integer banios;
    private Integer dormitorios;
    private String observaciones;
}