package com.example.rentstate.posts.infraestructure.persistence.jpa.repositories;

import com.example.rentstate.posts.domain.model.entities.Property;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findAllByAuthorId(User author);
    List<Property> findAllByAvailable(Boolean isAvailabale);

}
