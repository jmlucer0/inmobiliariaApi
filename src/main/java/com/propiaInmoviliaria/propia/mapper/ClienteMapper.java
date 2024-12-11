package com.propiaInmoviliaria.propia.mapper;

import com.propiaInmoviliaria.propia.dtos.cliente.ClienteDto;
import com.propiaInmoviliaria.propia.model.Cliente;
import com.propiaInmoviliaria.propia.model.Direccion;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDto toDto(Cliente cliente) {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(cliente.getId());
        clienteDto.setName(cliente.getName());
        clienteDto.setLastname(cliente.getLastname());
        clienteDto.setPhoneNumber(cliente.getPhoneNumber());
        clienteDto.setActive(cliente.isActive());

        if (cliente.getDireccion() != null) {
            Direccion direccion = new Direccion();
            direccion.setId(cliente.getDireccion().getId());
            direccion.setStreet(cliente.getDireccion().getStreet());
            direccion.setNumber(cliente.getDireccion().getNumber());
            direccion.setCity(cliente.getDireccion().getCity());
            clienteDto.setDireccion(direccion);
        }

        return clienteDto;
    }

    public Cliente toEntity(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteDto.getId());
        cliente.setName(clienteDto.getName());
        cliente.setLastname(clienteDto.getLastname());
        cliente.setPhoneNumber(clienteDto.getPhoneNumber());
        cliente.setActive(clienteDto.isActive());

        if (clienteDto.getDireccion() != null) {
            Direccion direccion = new Direccion();
            direccion.setStreet(clienteDto.getDireccion().getStreet());
            direccion.setNumber(clienteDto.getDireccion().getNumber());
            direccion.setCity(clienteDto.getDireccion().getCity());
            cliente.setDireccion(direccion);
        }

        return cliente;
    }
}
