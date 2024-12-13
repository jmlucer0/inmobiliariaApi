package com.propiaInmoviliaria.propia.repository;

import com.propiaInmoviliaria.propia.model.Propietario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
    Page<Propietario> findPropietarioByNombre(String nombre, Pageable pageable);
    Page<Propietario> findAllPropietarioByActiveTrue(Pageable pageable);
}
