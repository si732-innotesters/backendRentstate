package com.example.rentstate.properties.infraestructure.persistence.jpa.repositories;

import com.example.rentstate.properties.domain.model.entities.Comment;
import com.example.rentstate.properties.domain.model.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
