package com.example.rentstate.properties.infraestructure.persistence.jpa.repositories;

import com.example.rentstate.properties.domain.model.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //List<Post> findAllByAuthorId(User userId);
}
