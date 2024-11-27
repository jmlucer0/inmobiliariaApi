package com.propiaInmoviliaria.propia.controller;

import com.propiaInmoviliaria.propia.security.AuthRequest;
import com.propiaInmoviliaria.propia.security.AuthUserRegister;
import com.propiaInmoviliaria.propia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Controller for Authentication")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    @Operation(
            summary = "Register User",
            description = "Resgister a new user",
            tags = {"Authentication", "Register"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Required data to register a new user.",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthUserRegister.class)
                    )
            )
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully.",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
            )
        }
    )
    public ResponseEntity registerUser(@RequestBody AuthUserRegister request) {
        try {
            userService.createUser(request);
            return ResponseEntity.ok("User registered successfully.");

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error al registrar el usuario.");
        }
    }


    @DeleteMapping("/users/{id}")
    @Operation(
            summary = "Delete User by ID",
            description = "Deletes a user from the system based on the provided ID.",
            tags = {"Authentication", "User Management"}

    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User not found.",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
            )
    }
    )
    public ResponseEntity deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login User",
            description = "Authenticates a user using their credentials and returns a JWT token if successful.",
            tags = {"Authentication", "Login"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials required for authentication.",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthRequest.class)
                    )
            )
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful. Returns a JWT token.",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
            )
         }
    )
    public ResponseEntity userLogin(@RequestBody AuthRequest request){
        try {
            String jwt = userService.login(request);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Error registering the user.");
        }

    }

}
