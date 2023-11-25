package com.example.rentstate.properties.infraestructure.persistence.jpa.repositories;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.entities.Post;
import com.example.rentstate.properties.domain.model.entities.Property;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Boolean existsByProperty(Property property);
    List<Post> getAllByPropertyAuthor(User author);

    @Transactional
    void deleteAllByProperty(Property property);

}
