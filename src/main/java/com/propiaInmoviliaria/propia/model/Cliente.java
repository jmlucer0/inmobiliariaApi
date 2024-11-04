package com.propiaInmoviliaria.propia.model;

import com.propiaInmoviliaria.propia.dtos.ClienteDto;
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
    Long id;

    String name;
    String lastname;

    @OneToOne(cascade = CascadeType.ALL)
    Direccion direccion;

    @OneToMany
    @ElementCollection
    List<Propiedad> propiedadList;

    String phoneNumber;
    boolean active;

    public Cliente(ClienteDto cliente) {

    }
}
