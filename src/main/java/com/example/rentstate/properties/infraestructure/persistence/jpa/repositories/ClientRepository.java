package com.example.rentstate.properties.infraestructure.persistence.jpa.repositories;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.entities.Client;
import com.example.rentstate.properties.domain.model.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByLessor(User lessor);
    Optional<Client> findByRentedProperty(Property property);

}
