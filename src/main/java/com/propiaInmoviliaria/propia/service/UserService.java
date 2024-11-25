package com.propiaInmoviliaria.propia.service;

import com.propiaInmoviliaria.propia.mapper.UserMapper;
import com.propiaInmoviliaria.propia.model.User;
import com.propiaInmoviliaria.propia.repository.UserRepository;
import com.propiaInmoviliaria.propia.security.AuthUserRegister;
import com.propiaInmoviliaria.propia.security.CustomAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final CustomAuthenticationProvider authenticationProvider;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, CustomAuthenticationProvider authenticationProvider, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    public User createUser(AuthUserRegister register) {
        if (userRepository.existsByUsername(register.getUsername())) {
            throw new RuntimeException("El usuario ya está registrado");
        }
        User user = authenticationProvider.createNewUser(register);
        System.out.println(user);
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}
