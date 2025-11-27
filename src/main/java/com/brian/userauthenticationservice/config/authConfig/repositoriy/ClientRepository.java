package com.brian.userauthenticationservice.config.authConfig.repositoriy;

import java.util.Optional;


import com.brian.userauthenticationservice.config.authConfig.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
	Optional<Client> findByClientId(String clientId);
}