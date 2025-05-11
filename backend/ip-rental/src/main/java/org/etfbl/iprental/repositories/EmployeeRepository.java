package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    public Optional<EmployeeEntity> findByUsername(String username);
}
