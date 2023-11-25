package com.example.rentstate.properties.shared.services.implementation;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.entities.Client;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.service.ClientService;
import com.example.rentstate.properties.infraestructure.persistence.jpa.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    @Override
    public Optional<Client> createOrUpdate(Client client) {
        return Optional.of(clientRepository.save(client));
    }

    @Override
    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> getAllByLessor(User lessor) {
        return clientRepository.findAllByLessor(lessor);
    }

    @Override
    public Optional<Client> findByRentedProperty(Property property) {
        return clientRepository.findByRentedProperty(property);
    }
}
