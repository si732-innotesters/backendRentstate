package com.example.rentstate.properties.domain.service;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.entities.Client;
import com.example.rentstate.properties.domain.model.entities.Property;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Optional<Client> createOrUpdate(Client client);

    Optional<Client> getById(Long id);
    List<Client> getAllByLessor(User lessor);

    Optional<Client> findByRentedProperty(Property property);

}
