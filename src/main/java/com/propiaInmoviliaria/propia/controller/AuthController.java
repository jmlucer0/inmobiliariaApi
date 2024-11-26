package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.security.AuthRequest;
import com.propiaInmoviliaria.propia.security.AuthUserRegister;
import com.propiaInmoviliaria.propia.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody AuthUserRegister request) {
        try {
            userService.createUser(request);
            return ResponseEntity.ok("Usuario registrado exitosamente!");

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error al registrar el usuario.");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody AuthRequest request){
        try {
            String jwt = userService.login(request);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Nombre de usuario o contraseña inválidos.");
        }

    }

}
