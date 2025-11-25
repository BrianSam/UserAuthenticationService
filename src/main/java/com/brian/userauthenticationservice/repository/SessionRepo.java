package com.brian.userauthenticationservice.repository;

import com.brian.userauthenticationservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepo extends JpaRepository<Session, Long> {
}
