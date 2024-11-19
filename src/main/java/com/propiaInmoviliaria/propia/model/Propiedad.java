package com.propiaInmoviliaria.propia.model;

import com.propiaInmoviliaria.propia.dtos.PropiedadDto;
import com.propiaInmoviliaria.propia.util.Disponibilidad;
import com.propiaInmoviliaria.propia.util.TipoDePropiedad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    Cliente cliente;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Direccion direccion;

    Double superficie;
    Long precio;

    List<String> imagenes;

    TipoDePropiedad tipoDePropiedad;
    Disponibilidad disponibilidad;
    Boolean cochera;
    Boolean patio;

    public Propiedad(PropiedadDto propiedadDto) {

    }
}
