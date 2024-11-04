package com.propiaInmoviliaria.propia.service;

import com.propiaInmoviliaria.propia.dtos.ClienteDto;
import com.propiaInmoviliaria.propia.mapper.ClienteMapper;
import com.propiaInmoviliaria.propia.model.Cliente;
import com.propiaInmoviliaria.propia.model.Direccion;
import com.propiaInmoviliaria.propia.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper mapper;

    @Autowired
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

    public Cliente saveCliente(ClienteDto cliente){
        Cliente newCliente = new Cliente(cliente);

        if(cliente != null){
            updateField(cliente.getName(), newCliente::setName);
            updateField(cliente.getLastname(), newCliente::setLastname);
            updateField(cliente.getPhoneNumber(), newCliente::setPhoneNumber);
            newCliente.setActive(true);
            if (cliente.getDireccion() != null){
                newCliente.setDireccion(guardarDireccion(cliente.getDireccion()));
            }
        }
        clienteRepository.save(newCliente);
        return newCliente;
    }

    public Page<Cliente> findClienteByName(String name, Pageable pageable){
        return clienteRepository.findClienteByName(name, pageable);
    }

    public Cliente searchById(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        return cliente;
    }

    @Transactional
    public Cliente updateCliente(ClienteDto clienteDto){
        Cliente cliente = clienteRepository.findById(clienteDto.getId()).orElseThrow(()->new EntityNotFoundException("Cliente no encontrado"));

        updateField(clienteDto.getName(), cliente::setName);
        updateField(clienteDto.getLastname(), cliente::setLastname);
        updateField(clienteDto.getPhoneNumber(), cliente::setPhoneNumber);

        if (clienteDto.getDireccion() != null){
            cliente.setDireccion(guardarDireccion(cliente.getDireccion()));
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

    public Direccion guardarDireccion(Direccion newDireccion){
        Direccion direccion = new Direccion();
            updateField(newDireccion.getStreet(), direccion::setStreet);
            updateField(newDireccion.getNumber(), direccion::setNumber);
            updateField(newDireccion.getCity(), direccion::setCity);

        return direccion;
    }

    private <T> void updateField (T field, Consumer<T> setter){
        if (field != null){
            setter.accept(field);
        }
    }
}
