package com.propiaInmoviliaria.propia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    Cliente cliente;

    @OneToOne(cascade = CascadeType.ALL)
    Direccion direccion;
}
