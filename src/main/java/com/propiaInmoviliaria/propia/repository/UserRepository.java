package com.propiaInmoviliaria.propia.repository;

import com.propiaInmoviliaria.propia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
