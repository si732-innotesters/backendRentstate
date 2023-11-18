package com.example.rentstate.properties.domain.service;

import com.example.rentstate.properties.api.resource.UpdatePropertyResource;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PropertyService {

    Optional<Property> getById(Long propertyId);
    Optional<Property> create(Property property);
    Optional<Property> update(UpdatePropertyResource updatePropertyResource);
    ResponseEntity<?> delete(Long propertyId);
    List<Property> getByAuthor(User author);

}
