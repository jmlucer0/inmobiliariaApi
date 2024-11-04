package com.propiaInmoviliaria.propia.repository;

import com.propiaInmoviliaria.propia.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findClienteByName(String name, Pageable pageable);
    Page<Cliente> findAllClienteByActiveTrue(Pageable pageable);
}
