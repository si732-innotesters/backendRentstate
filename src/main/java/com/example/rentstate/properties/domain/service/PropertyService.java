package com.example.rentstate.properties.domain.service;

import com.example.rentstate.properties.api.resource.propertyResource.UpdatePropertyResource;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PropertyService {

    Optional<Property> getById(Long propertyId);
    Optional<Property> create(Property property);
    Optional<Property> update(Property property);
    ResponseEntity<?> delete(Long propertyId);
    List<Property> getAllByAuthor(User author);
    List<Property> getAvailableProperty(Boolean available);

    public List<User> getReservedUsersForProperty(Long propertyId);
    ResponseEntity<?> reserveProperty(Property property, User user);
    ResponseEntity<?> cancelReservation(Property property, User user);

}
