package com.propiaInmoviliaria.propia.repository;

import com.propiaInmoviliaria.propia.model.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
}
