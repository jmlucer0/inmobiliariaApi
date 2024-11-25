package com.propiaInmoviliaria.propia.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserRegister {

    String username;
    String password;
    String email;
    Role roleName;
}
