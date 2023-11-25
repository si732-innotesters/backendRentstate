package com.example.rentstate.properties.domain.service;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.api.resource.postResource.UpdatePostResource;
import com.example.rentstate.properties.domain.model.entities.Post;
import com.example.rentstate.properties.domain.model.entities.Property;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> getById(Long postId);
    List<Post> getAllPosts();
    Optional<Post> create(Post post);
    Optional<Post> update(UpdatePostResource updatePostResource);
    ResponseEntity<?> delete(Long postId);
    List<Post> getAllPostsByAuthor (User author);

    void deleteAllPostByProperty(Property property);

}
