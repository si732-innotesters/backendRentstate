package com.example.rentstate.properties.infraestructure.persistence.jpa.repositories;

import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findAllByAuthor(User author);
    List<Property> findAllByAvailable (Boolean Available);

}
