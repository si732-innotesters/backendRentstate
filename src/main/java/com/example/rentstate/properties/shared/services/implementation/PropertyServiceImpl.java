package com.example.rentstate.properties.shared.services.implementation;

import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.rentstate.properties.api.resource.propertyResource.UpdatePropertyResource;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.service.PropertyService;
import com.example.rentstate.properties.infraestructure.persistence.jpa.repositories.PropertyRepository;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.shared.exceptions.ResourceNotFoundException;
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
    public Optional<Property> update(UpdatePropertyResource resource) {

        Optional<Property>property = propertyRepository.findById(resource.getId());
        Optional<User>renter = userRepository.findById(resource.getRenterId());
        if(renter.isPresent())
            property.get().setRenter(renter.get());

        return Optional.ofNullable((property
                .map(propertyToUpdate ->
                        propertyRepository.save(propertyToUpdate
                                .withName(resource.getName())
                                .withDescription(resource.getDescription())
                                .withCategory(resource.getCategory())
                                .withAvailable(resource.isAvailable())
                                .withCharacteristics(resource.getCharacteristics())
                                .withLocation(resource.getLocation())
                                .withUrlImg(resource.getUrlImg())
                        ))
                .orElseThrow(()->new ResourceNotFoundException("Property", resource.getId()))));
    }

    @Override
    public ResponseEntity<?> delete(Long propertyId) {
        return propertyRepository.findById(propertyId).map(property -> {
            propertyRepository.delete(property);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException("Property", propertyId));
    }

    @Override
    public List<Property> getByAuthor(User author) {
        return propertyRepository.findAllByAuthor(author);
    }

}
