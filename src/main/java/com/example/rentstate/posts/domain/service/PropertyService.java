package com.example.rentstate.posts.domain.service;

import com.example.rentstate.posts.domain.model.entities.Property;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PropertyService {

    List<Property> getAll();
    Page<Property> getAll(Pageable pageable);

    Optional<Property> getById(Long propertyId);

    Optional<Property> create(Property property);

    Optional<Property> update(Long propertyId, Property resource);

    Optional<Property> updateRenter(Long propertyId, User renter);

    Optional<Property> updateAvailable(Long propertyId, Boolean isAvailable);

    ResponseEntity<?> delete(Long propertyId);

    List<Property> getByAuthorId(User author);

    List<Property> getByAvailable(Boolean isAvailable);

}
