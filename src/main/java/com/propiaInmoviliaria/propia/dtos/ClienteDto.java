package com.propiaInmoviliaria.propia.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.propiaInmoviliaria.propia.model.Cliente;
import com.propiaInmoviliaria.propia.model.Direccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDto {
    Long id;
    String name;
    String lastname;
    Direccion direccion;
    //List<Propiedad> propiedadList;
    String phoneNumber;
    boolean active;

    public ClienteDto(Cliente cliente) {

    }
}
