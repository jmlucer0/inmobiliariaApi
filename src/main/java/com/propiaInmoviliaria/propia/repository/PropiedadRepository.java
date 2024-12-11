package com.propiaInmoviliaria.propia.repository;

import com.propiaInmoviliaria.propia.model.Propiedad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {

    @Query("SELECT p FROM Propiedad p INNER JOIN p.direccion d WHERE d.street = :street OR d.number = :number OR d.city = :city")
    Page<Propiedad> findByStreetAndNumberAndCity(
            @Param("street") String street,
            @Param("number") String number,
            @Param("city") String city,
            Pageable pageable);
}
