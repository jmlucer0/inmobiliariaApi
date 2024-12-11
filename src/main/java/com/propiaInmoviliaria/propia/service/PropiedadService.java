package com.propiaInmoviliaria.propia.service;

import com.propiaInmoviliaria.propia.dtos.propiedad.CrearPropiedadDto;
import com.propiaInmoviliaria.propia.dtos.propiedad.PropiedadDto;
import com.propiaInmoviliaria.propia.mapper.PropiedadMapper;
import com.propiaInmoviliaria.propia.model.Cliente;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.repository.ClienteRepository;
import com.propiaInmoviliaria.propia.repository.PropiedadRepository;
import com.propiaInmoviliaria.propia.util.updater.PropiedadUpdater;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class PropiedadService {

    private final PropiedadRepository propiedadRepository;
    private final ClienteRepository clienteRepository;
    private final PropiedadMapper mapper;
    private final PropiedadUpdater propiedadUpdater;


    public PropiedadService(PropiedadRepository propiedadRepository, ClienteRepository clienteRepository, PropiedadMapper mapper, PropiedadUpdater propiedadUpdater) {
        this.propiedadRepository = propiedadRepository;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
        this.propiedadUpdater = propiedadUpdater;
    }

    @Transactional
    public Propiedad savePropiedad(CrearPropiedadDto propiedadDto) {
        if (propiedadDto == null) {
            throw new IllegalArgumentException("Propiedad no puede ser nulo");
        }
        Propiedad newPropiedad = mapper.toEntityFromCrearPropiedad(propiedadDto);
        if (propiedadDto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(propiedadDto.getClienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
            newPropiedad.setCliente(cliente);
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
    public Propiedad actualizarPropiedad(Long id, CrearPropiedadDto propiedadDto){
        Propiedad propiedad = propiedadRepository.findById(id).orElseThrow(()->new EntityNotFoundException(("Propiedad no enontrada")));
        Cliente cliente = clienteRepository.findById(propiedadDto.getClienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        boolean actualizado = propiedadUpdater.actualizarPropiedad(propiedad, propiedadDto, cliente);

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

    public Page<PropiedadDto> buscarPorCalleYNumero(String calle, String numero, String city, Pageable pageable){
        Page<Propiedad> propiedades = propiedadRepository.findByStreetAndNumberAndCity(calle, numero, city, pageable);
        return propiedades.map(mapper::propiedadDto);
    }
}
