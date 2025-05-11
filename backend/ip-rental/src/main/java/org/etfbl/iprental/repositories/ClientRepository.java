package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

    Optional<ClientEntity> findByUsername(String username);
}
