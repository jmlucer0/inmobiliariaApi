package com.propiaInmoviliaria.propia.mapper;

import com.propiaInmoviliaria.propia.dtos.ClienteDto;
import com.propiaInmoviliaria.propia.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDto toDto(Cliente cliente){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(cliente.getId());
        clienteDto.setName(clienteDto.getName());
        clienteDto.setLastname(clienteDto.getLastname());
        clienteDto.setPhoneNumber(clienteDto.getPhoneNumber());
        clienteDto.setActive(clienteDto.isActive());
        return clienteDto;
    }

}
