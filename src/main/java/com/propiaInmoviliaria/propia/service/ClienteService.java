package com.propiaInmoviliaria.propia.service;

import com.propiaInmoviliaria.propia.dtos.ClienteDto;
import com.propiaInmoviliaria.propia.mapper.ClienteMapper;
import com.propiaInmoviliaria.propia.model.Cliente;
import com.propiaInmoviliaria.propia.model.Direccion;
import com.propiaInmoviliaria.propia.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static com.propiaInmoviliaria.propia.util.FieldUpdater.updateField;



@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper mapper;


    public ClienteService(ClienteRepository clienteRepository, ClienteMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    public Page<ClienteDto> findAllActiveCliente(Pageable pageable){
        Page<Cliente> clientes = clienteRepository.findAllClienteByActiveTrue(pageable);
        if (clientes.isEmpty()){
            return clientes.map(mapper::toDto);
        }
        System.out.println(clientes.map(mapper::toDto));
        return clientes.map(mapper::toDto);
    }

    @Transactional
    public Cliente saveCliente(ClienteDto clienteDto){
        Cliente cliente = mapper.toEntity(clienteDto);
        cliente.setActive(true);
        clienteRepository.save(cliente);
        return cliente;
    }

    public Page<Cliente> findClienteByName(String name, Pageable pageable){
        return clienteRepository.findClienteByName(name, pageable);
    }

    public Cliente searchById(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        return cliente;
    }

    @Transactional
    public Cliente updateCliente(Long id, ClienteDto clienteDto){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Cliente no encontrado"));
        updateField(clienteDto.getName(), cliente::setName);
        updateField(clienteDto.getLastname(), cliente::setLastname);
        updateField(clienteDto.getPhoneNumber(), cliente::setPhoneNumber);

        if (clienteDto.getDireccion() != null) {
            Direccion direccion = cliente.getDireccion() != null ? cliente.getDireccion() : new Direccion();
            updateField(clienteDto.getDireccion().getStreet(), direccion::setStreet);
            updateField(clienteDto.getDireccion().getNumber(), direccion::setNumber);
            updateField(clienteDto.getDireccion().getCity(), direccion::setCity);

            cliente.setDireccion(direccion);
        }
        return cliente;
    }

    @Transactional
    public boolean deleteClienteById(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("cliente no encontrado"));
        if (cliente.isActive()){
            cliente.setActive(false);
            return true;
        }
        return true;
    }

}
