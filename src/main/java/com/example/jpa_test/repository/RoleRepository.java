package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.Role;
import com.example.jpa_test.model.enums.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByUserAuthority(UserAuthority userAuthority);
}
