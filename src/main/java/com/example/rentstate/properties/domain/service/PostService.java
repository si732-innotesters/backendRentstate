package com.example.rentstate.properties.domain.service;

import com.example.rentstate.properties.domain.model.entities.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> getById(Long postId);
    List<Post> getAllPosts();
    Optional<Post> create(Post post);
    Optional<Post> update(Post post);
    ResponseEntity<?> delete(Long postId);
    //List<Post> getPostsByAuthorId(User userId);
}
