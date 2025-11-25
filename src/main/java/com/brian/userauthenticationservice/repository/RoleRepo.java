package com.brian.userauthenticationservice.repository;

import com.brian.userauthenticationservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
