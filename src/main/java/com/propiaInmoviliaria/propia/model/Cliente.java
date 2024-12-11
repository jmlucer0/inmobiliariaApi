package com.propiaInmoviliaria.propia.model;

import com.propiaInmoviliaria.propia.dtos.cliente.ClienteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    @OneToOne(cascade = CascadeType.ALL)
    private Direccion direccion;

    @OneToMany
    @ElementCollection
    private List<Propiedad> propiedadList;

    private String phoneNumber;
    private boolean active;

    public Cliente(ClienteDto cliente) {

    }
}
