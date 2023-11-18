package com.example.rentstate.posts.shared.services.implementation;

import com.example.rentstate.posts.domain.model.entities.Property;
import com.example.rentstate.posts.domain.service.PropertyService;
import com.example.rentstate.posts.infraestructure.persistence.jpa.repositories.PropertyRepository;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.service.UserService;
import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.rentstate.shared.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserService userService;

    public PropertyServiceImpl(PropertyRepository propertyRepository, UserService userService) {
        this.propertyRepository = propertyRepository;
        this.userService = userService;
    }

    @Override
    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    @Override
    public Page<Property> getAll(Pageable pageable) {
        return propertyRepository.findAll(pageable);
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
    public Optional<Property> update(Long propertyId, Property resource) {
        return Optional.ofNullable((propertyRepository.findById(resource.getId())
                .map(propertyToUpdate ->
                        propertyRepository.save(propertyToUpdate
                                .withName(resource.getName())
                                .withDescription(resource.getDescription())
                                .withCategory(resource.getCategory())
                                .withAvailable(resource.getAvailable())
                                .withCharacteristics(resource.getCharacteristics())
                                .withLocation(resource.getLocation())
                                .withRenterId(resource.getRenterId())
                        ))
                .orElseThrow(()->new ResourceNotFoundException("Property", resource.getId()))));
    }

    @Override
    public Optional<Property> updateRenter(Long propertyId, User renter) {
        return Optional.ofNullable(propertyRepository.findById(propertyId)
                .map(propertyToUpdate->
                        propertyRepository.save(propertyToUpdate
                                .withRenterId(renter)
                        ))
                .orElseThrow(()->new ResourceNotFoundException("Property", propertyId)));
    }

    @Override
    public Optional<Property> updateAvailable(Long propertyId, Boolean isAvailable) {
        return Optional.ofNullable(propertyRepository.findById(propertyId)
                .map(propertyToUpdate->
                        propertyRepository.save(propertyToUpdate
                                .withAvailable(isAvailable)
                        ))
                .orElseThrow(()->new ResourceNotFoundException("Property", propertyId)));
    }

    @Override
    public ResponseEntity<?> delete(Long propertyId) {
        return propertyRepository.findById(propertyId).map(property -> {
            propertyRepository.delete(property);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException("Property", propertyId));
    }

    @Override
    public List<Property> getByAuthorId(User author) {
        return propertyRepository.findAllByAuthorId(author);
    }

    @Override
    public List<Property> getByAvailable(Boolean isAvailable) {
        return propertyRepository.findAllByAvailable(isAvailable);
    }
}
