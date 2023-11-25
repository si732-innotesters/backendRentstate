package com.example.rentstate.properties.shared.services.implementation;

import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.rentstate.properties.api.resource.propertyResource.UpdatePropertyResource;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.service.PropertyService;
import com.example.rentstate.properties.infraestructure.persistence.jpa.repositories.PropertyRepository;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.shared.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Property> getById(Long propertyId) {
        return Optional.ofNullable(propertyRepository.findById(propertyId)
                .orElseThrow(()->new ResourceNotFoundException("Property", propertyId)));
    }

    @Override
    public Optional<Property> create(Property property) {
        return Optional.of(propertyRepository.save(property));
    }

    @Override
    @Transactional
    public Optional<Property> update(Property property) {

        System.out.println(property.getName());
        return Optional.of(propertyRepository.saveAndFlush(property));
    }


    @Override
    public ResponseEntity<?> delete(Long propertyId) {
        return propertyRepository.findById(propertyId).map(property -> {
            propertyRepository.delete(property);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException("Property", propertyId));
    }

    @Override
    public List<Property> getAllByAuthor(User author) {
        return propertyRepository.findAllByAuthor(author);
    }

    @Override
    public List<Property> getAvailableProperty(Boolean available) {
        return propertyRepository.findAllByAvailable(available);
    }


    public List<User> getReservedUsersForProperty(Long propertyId) {
        return propertyRepository.findReservedUsersById(propertyId);
    }

    @Override
    public ResponseEntity<?> reserveProperty(Property property, User user) {

        property.getReservedByUsers().add(user);
        propertyRepository.save(property);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> cancelReservation(Property property, User user) {

        property.getReservedByUsers().remove(user);
        propertyRepository.save(property);

        return ResponseEntity.noContent().build();
    }
}
