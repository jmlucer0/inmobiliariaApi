package com.propiaInmoviliaria.propia.model;

import com.propiaInmoviliaria.propia.enums.Barrio;
import com.propiaInmoviliaria.propia.enums.Localidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String calle;
    String numero;
    Localidad localidad;
    Barrio barrio;
}
