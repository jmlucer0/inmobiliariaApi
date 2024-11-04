package com.propiaInmoviliaria.propia.service;

import com.propiaInmoviliaria.propia.repository.PropiedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropiedadService {

    private final PropiedadRepository propiedadRepository;

    @Autowired
    public PropiedadService(PropiedadRepository propiedadRepository) {
        this.propiedadRepository = propiedadRepository;
    }
}
