package com.example.rentstate.properties.shared.services.implementation;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.api.resource.postResource.UpdatePostResource;
import com.example.rentstate.properties.domain.model.entities.Post;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.service.PostService;
import com.example.rentstate.properties.infraestructure.persistence.jpa.repositories.PostRepository;
import com.example.rentstate.properties.infraestructure.persistence.jpa.repositories.PropertyRepository;
import com.example.rentstate.shared.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PropertyRepository propertyRepository;


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

        if(postRepository.existsByProperty(post.getProperty()) ){
            throw new IllegalArgumentException("Post with same property id already exists");
        }
        post.getProperty().setIsPosted(true);
        propertyRepository.save(post.getProperty());
        return Optional.of(postRepository.save(post));
    }

    @Override
    public Optional<Post> update(UpdatePostResource updatePostResource) {
        return Optional.ofNullable(postRepository.findById(updatePostResource.getId()).map(newPost->
                postRepository.save(newPost
                        .withTitle(updatePostResource.getTitle())
                        .withPrice(updatePostResource.getPrice())
                )).orElseThrow(()->new ResourceNotFoundException("Post", updatePostResource.getId())));
    }

    @Override
    public ResponseEntity<?> delete(Long postId) {
        var property = postRepository.getById(postId).getProperty();
        property.setIsPosted(false);
        propertyRepository.save(property);

        return postRepository.findById(postId).map(post -> {
                    postRepository.delete(post);
                    return ResponseEntity.ok().build();})
                .orElseThrow(() -> new ResourceNotFoundException("Post", postId));
    }

    @Override
    public List<Post> getAllPostsByAuthor(User author) {
        return postRepository.getAllByPropertyAuthor(author);
    }

    @Override
    @Transactional
    public void deleteAllPostByProperty(Property property) {
        postRepository.deleteAllByProperty(property);
    }


}
