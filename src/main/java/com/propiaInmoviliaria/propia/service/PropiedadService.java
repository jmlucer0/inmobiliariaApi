package com.propiaInmoviliaria.propia.service;

import com.propiaInmoviliaria.propia.dtos.PropiedadDto;
import com.propiaInmoviliaria.propia.mapper.PropiedadMapper;
import com.propiaInmoviliaria.propia.model.Cliente;
import com.propiaInmoviliaria.propia.model.Direccion;
import com.propiaInmoviliaria.propia.model.Propiedad;
import com.propiaInmoviliaria.propia.repository.ClienteRepository;
import com.propiaInmoviliaria.propia.repository.PropiedadRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static com.propiaInmoviliaria.propia.util.FieldUpdater.updateField;


@Service
public class PropiedadService {

    private final PropiedadRepository propiedadRepository;
    private final ClienteRepository clienteRepository;
    private final PropiedadMapper mapper;


    public PropiedadService(PropiedadRepository propiedadRepository, ClienteRepository clienteRepository, PropiedadMapper mapper) {
        this.propiedadRepository = propiedadRepository;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Propiedad savePropiedad(PropiedadDto propiedadDto) {
        if (propiedadDto == null) {
            throw new IllegalArgumentException("Propiedad no puede ser nulo");
        }
        Propiedad newPropiedad = mapper.toEntity(propiedadDto);
        if (propiedadDto.getCliente() != null) {
            Cliente cliente = clienteRepository.findById(propiedadDto.getCliente())
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
    public Propiedad actualizarPropiedad(Long id, PropiedadDto propiedadDto){
        Propiedad propiedad = propiedadRepository.findById(id).orElseThrow(()->new EntityNotFoundException(("Propiedad no enontrada")));
        if (propiedadDto.getCliente() != null) {
            Cliente cliente = clienteRepository.findById(propiedadDto.getCliente())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
            propiedad.setCliente(cliente);
        }

        updateField(propiedadDto.getTipoDePropiedad(), propiedad::setTipoDePropiedad);
        updateField(propiedadDto.getDisponibilidad(), propiedad::setDisponibilidad);
        updateField(propiedadDto.getImagenes(), propiedad::setImagenes);
        updateField(propiedadDto.getPatio(), propiedad::setPatio);
        updateField(propiedadDto.getCochera(), propiedad::setCochera);
        updateField(propiedadDto.getPrecio(), propiedad::setPrecio);
        updateField(propiedadDto.getSuperficie(), propiedad::setSuperficie);

        if (propiedadDto.getDireccion() != null) {
            Direccion direccion = propiedad.getDireccion() != null ? propiedad.getDireccion() : new Direccion();
            updateField(propiedadDto.getDireccion().getStreet(), direccion::setStreet);
            updateField(propiedadDto.getDireccion().getNumber(), direccion::setNumber);
            updateField(propiedadDto.getDireccion().getCity(), direccion::setCity);

            propiedad.setDireccion(direccion);
        }
        return propiedad;
    }

    public boolean borrarPropiedad(Long id){
        Propiedad propiedad = propiedadRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Propiedad no encontrada"));
        propiedadRepository.deleteById(propiedad.getId());
        if (propiedadRepository.findById(propiedad.getId()).isPresent()){
            return false;
        }
        return true;
    }
}
