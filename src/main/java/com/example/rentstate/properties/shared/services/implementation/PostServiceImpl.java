package com.example.rentstate.properties.shared.services.implementation;

import com.example.rentstate.properties.domain.model.entities.Post;
import com.example.rentstate.properties.domain.service.PostService;
import com.example.rentstate.properties.infraestructure.persistence.jpa.repositories.PostRepository;
import com.example.rentstate.shared.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Post> getById(Long postId) {
        return Optional.ofNullable(postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post", postId)));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> create(Post post) {
        Long propertyId = post.getPropertyId().getId();
        if(postRepository.existsById(propertyId)){
            throw new IllegalArgumentException("Post with same property id already exists");
        }
        return Optional.of(postRepository.save(post));
    }

    @Override
    public Optional<Post> update( Post post) {
        return Optional.ofNullable(postRepository.findById(post.getId()).map(newPost->
                postRepository.save(newPost
                        .withTitle(post.getTitle())
                        .withPrice(post.getPrice())
                )).orElseThrow(()->new ResourceNotFoundException("Post", post.getId())));
    }

    @Override
    public ResponseEntity<?> delete(Long postId) {
        return postRepository.findById(postId).map(post -> {
                    postRepository.delete(post);
                    return ResponseEntity.ok().build();})
                .orElseThrow(() -> new ResourceNotFoundException("Post", postId));
    }

}
