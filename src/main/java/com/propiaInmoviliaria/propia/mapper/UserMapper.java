package com.propiaInmoviliaria.propia.mapper;

import com.propiaInmoviliaria.propia.model.User;
import com.propiaInmoviliaria.propia.security.AuthUserRegister;
import com.propiaInmoviliaria.propia.security.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(AuthUserRegister userRegister) {
        User user = new User();
        user.setUsername(userRegister.getUsername());
        user.setPassword(userRegister.getPassword());
        user.setEmail(userRegister.getEmail());
        Role role = Role.valueOf(userRegister.getRoleName().name());
        user.setRole(role);
        user.setRole(userRegister.getRoleName());
        return user;
    }
}
