package com.propiaInmoviliaria.propia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Propietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Direccion direccion;

    @OneToMany
    @ElementCollection
    private List<Propiedad> propiedadList;

    private String telefono;
    private String nombre;
    private String apellido;
    private String email;
    private Boolean active;

}
