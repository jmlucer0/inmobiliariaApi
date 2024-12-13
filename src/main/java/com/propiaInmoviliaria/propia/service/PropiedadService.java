package com.propiaInmoviliaria.propia.service;

import com.propiaInmoviliaria.propia.dtos.propiedad.ActualizarPropiedadDto;
import com.propiaInmoviliaria.propia.dtos.propiedad.CrearPropiedadDto;
import com.propiaInmoviliaria.propia.dtos.propiedad.PropiedadDto;
import com.propiaInmoviliaria.propia.mapper.PropiedadMapper;
import com.propiaInmoviliaria.propia.model.Propietario;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.repository.PropietarioRepository;
import com.propiaInmoviliaria.propia.repository.PropiedadRepository;
import com.propiaInmoviliaria.propia.util.updater.DireccionUpdater;
import com.propiaInmoviliaria.propia.util.updater.PropiedadUpdater;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class PropiedadService {

    private final PropiedadRepository propiedadRepository;
    private final PropietarioRepository propietarioRepository;
    private final PropiedadMapper mapper;
    private final PropiedadUpdater propiedadUpdater;
    private final DireccionUpdater direccionUpdater;


    public PropiedadService(PropiedadRepository propiedadRepository, PropietarioRepository propietarioRepository, PropiedadMapper mapper, PropiedadUpdater propiedadUpdater, DireccionUpdater direccionUpdater) {
        this.propiedadRepository = propiedadRepository;
        this.propietarioRepository = propietarioRepository;
        this.mapper = mapper;
        this.propiedadUpdater = propiedadUpdater;
        this.direccionUpdater = direccionUpdater;
    }

    @Transactional
    public Propiedad savePropiedad(CrearPropiedadDto propiedadDto) {
        if (propiedadDto == null) {
            throw new IllegalArgumentException("Propiedad no puede ser nulo");
        }
        Propiedad newPropiedad = mapper.toEntityFromCrearPropiedad(propiedadDto);
        if (propiedadDto.getPropietarioId() != null) {
            Propietario propietario = propietarioRepository.findById(propiedadDto.getPropietarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
            newPropiedad.setPropietario(propietario);
        }
        propiedadRepository.save(newPropiedad);
        return newPropiedad;
    }

    public Propiedad buscarPorId(Long id){
        Propiedad propiedad = propiedadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Propiedad no encontrada"));
        return propiedad;
    }

    public Page<PropiedadDto> listarPropiedades(Pageable pageable){
        Page<Propiedad> propiedades = propiedadRepository.findAll(pageable);
        if ((propiedades.isEmpty())){
            return propiedades.map(mapper::propiedadDto);
        }
        return propiedades.map(mapper::propiedadDto);
    }

    @Transactional
    public Propiedad actualizarPropiedad(Long id, ActualizarPropiedadDto propiedadDto){
        Propiedad propiedad = propiedadRepository.findById(id).orElseThrow(()->new EntityNotFoundException(("Propiedad no enontrada")));
        Propietario propietario = propietarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Propietario no encontrado"));

        System.out.println("Propiedad existente: " + propiedad);
        System.out.println("Propietario existente: " + propiedad.getPropietario().getId());
        System.out.println("Propietario ID en DTO: " + propiedadDto.getPropietarioId());
        boolean actualizado = propiedadUpdater.actualizarPropiedad(propiedad, propiedadDto, propietario);

        if (propiedadDto.getDireccion() != null && propiedad.getDireccion() != null){
            actualizado |= direccionUpdater.actualizarDireccion(propiedad.getDireccion(), propiedadDto.getDireccion());
        }
        if (actualizado){
            return propiedadRepository.save(propiedad);
        }
        return propiedad;
    }

    //crear un metodo para que actualize las imagenes con otro endpoint

    public boolean borrarPropiedad(Long id){
        Propiedad propiedad = propiedadRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Propiedad no encontrada"));
        propiedadRepository.deleteById(propiedad.getId());
        if (propiedadRepository.findById(propiedad.getId()).isPresent()){
            return false;
        }
        return true;
    }

}
