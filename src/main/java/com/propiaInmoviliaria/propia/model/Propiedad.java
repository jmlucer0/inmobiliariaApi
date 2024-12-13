package com.propiaInmoviliaria.propia.model;

import com.propiaInmoviliaria.propia.enums.Disponibilidad;
import com.propiaInmoviliaria.propia.enums.TipoDeOperacion;
import com.propiaInmoviliaria.propia.enums.TipoDePropiedad;
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
    private Long id;

    private String numeroDeReferencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Direccion direccion;

    private List<String> imagenes;
    private Double superficieTotal;
    private Double superficieCubierta;
    private Long precio;

    private TipoDePropiedad tipoDePropiedad;
    private Disponibilidad disponibilidad;
    private TipoDeOperacion tipoDeOperacion;
    private Boolean cochera;
    private Boolean patio;
    private Integer banios;
    private Integer dormitorios;
    private String obsevaciones;

}
