package com.propiaInmoviliaria.propia.service;

import com.propiaInmoviliaria.propia.dtos.propietario.ActualizarPropietarioDto;
import com.propiaInmoviliaria.propia.dtos.propietario.CrearPropietarioDto;
import com.propiaInmoviliaria.propia.dtos.propietario.PropietarioDto;
import com.propiaInmoviliaria.propia.mapper.PropietarioMapper;
import com.propiaInmoviliaria.propia.model.Propietario;
import com.propiaInmoviliaria.propia.repository.PropietarioRepository;
import com.propiaInmoviliaria.propia.util.updater.DireccionUpdater;
import com.propiaInmoviliaria.propia.util.updater.PropiedadUpdater;
import com.propiaInmoviliaria.propia.util.updater.PropietarioUpdater;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;




@Service
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;
    private final PropietarioMapper propietarioMapper;
    private final DireccionUpdater direccionUpdater;
    private final PropietarioUpdater propietarioUpdater;


    public PropietarioService(PropietarioRepository propietarioRepository, PropietarioMapper propietarioMapper, DireccionUpdater direccionUpdater, PropiedadUpdater propiedadUpdater, PropietarioUpdater propietarioUpdater) {
        this.propietarioRepository = propietarioRepository;
        this.propietarioMapper = propietarioMapper;
        this.direccionUpdater = direccionUpdater;
        this.propietarioUpdater = propietarioUpdater;
    }

    public Page<PropietarioDto> findAllActivePropietario(Pageable pageable){
        Page<Propietario> propietarios = propietarioRepository.findAllPropietarioByActiveTrue(pageable);
        if (propietarios.isEmpty()){
            return propietarios.map(propietarioMapper::toDto);
        }
        return propietarios.map(propietarioMapper::toDto);
    }

    @Transactional
    public Propietario savePropietario(CrearPropietarioDto propietarioDto){
        Propietario propietario = propietarioMapper.toEntityFromCrearPropietario(propietarioDto);
        propietario.setActive(true);
        propietarioRepository.save(propietario);
        return propietario;
    }

    public Page<Propietario> findClienteByName(String nombre, Pageable pageable){
        return propietarioRepository.findPropietarioByNombre(nombre, pageable);
    }

    public Propietario searchById(Long id){
        return propietarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }

    @Transactional
    public Propietario updatePropietario(Long id, ActualizarPropietarioDto propietarioDto){
        Propietario propietario = propietarioRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Cliente no encontrado"));
        boolean actualizado = propietarioUpdater.actualizarPropietario(propietario, propietarioDto);

        if (propietarioDto.getDireccion() != null && propietario.getDireccion() != null) {
            actualizado |= direccionUpdater.actualizarDireccion(propietario.getDireccion(), propietarioDto.getDireccion());
        }
        if (actualizado){
            return propietarioRepository.save(propietario);
        }
        return propietario;
    }

    @Transactional
    public boolean deletePropietarioById(Long id){
        Propietario propietario = propietarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("cliente no encontrado"));
        if (propietario.getActive()){
            propietario.setActive(false);
            return true;
        }
        return true;
    }

}
