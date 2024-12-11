package com.propiaInmoviliaria.propia.dtos.propiedad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.propiaInmoviliaria.propia.enums.TipoDeOperacion;
import com.propiaInmoviliaria.propia.model.Direccion;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.enums.Disponibilidad;
import com.propiaInmoviliaria.propia.enums.TipoDePropiedad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropiedadDto {

    private Long id;
    private Long cliente;
    private Long numeroDeReferencia;
    private Direccion direccion;
    private Double superficieTotal;
    private Double superficieCubierta;
    private Long precio;
    private List<String> imagenes;
    private TipoDePropiedad tipoDePropiedad;
    private TipoDeOperacion tipoDeOperacion;
    private Disponibilidad disponibilidad;
    private Boolean cochera;
    private Boolean patio;
    private Integer banios;
    private Integer dormitorios;
    private String observaciones;

    public PropiedadDto(Propiedad nuevaPropiedad) {

    }
}
