package com.propiaInmoviliaria.propia.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema
public class AuthRequest {

    @Schema
    private String username;
    @Schema
    private String password;
}
