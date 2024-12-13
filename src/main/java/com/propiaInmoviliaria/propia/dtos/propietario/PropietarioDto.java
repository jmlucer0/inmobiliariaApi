package com.propiaInmoviliaria.propia.dtos.propietario;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.propiaInmoviliaria.propia.model.Propietario;
import com.propiaInmoviliaria.propia.model.Direccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropietarioDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private Boolean active;
    private Direccion direccion;

}
