package com.example.rentstate.properties.api.rest;

import com.example.rentstate.properties.api.resource.postResource.PostResource;
import com.example.rentstate.properties.api.resource.postResource.PostResponse;
import com.example.rentstate.properties.api.resource.postResource.UpdatePostResource;
import com.example.rentstate.properties.domain.model.entities.Post;
import com.example.rentstate.properties.domain.service.PostService;
import com.example.rentstate.properties.domain.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/posts", produces = "application/json")
public class PostController {
    private final PostService postService;
    private final ModelMapper postMapper;
    private final PropertyService propertyService;


    public PostController(PostService postService, PropertyService propertyService, ModelMapper postMapper) {
        this.postService = postService;
        this.propertyService = propertyService;
        this.postMapper = postMapper;
    }
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostResource resource ){
        var propertyId = propertyService.getById(resource.getPropertyId());
        if(propertyId.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Post newPost = new Post(propertyId.get(),resource);
        var post = postService.create(newPost);
        if(post.isPresent()){
            var postResponse = new PostResponse(post.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public List<PostResponse> getAll() {
        List<Post>posts = postService.getAllPosts();
        List<PostResponse>postResponse = posts.stream()
                .map(post -> postMapper.map(post, PostResponse.class))
                .collect(Collectors.toList());
        return postResponse;
    }
    @GetMapping("{postId}")
    public  ResponseEntity<PostResponse> getPostById(@PathVariable Long postId){
        var post = postService.getById(postId);
        var postResponse = new PostResponse(post.get());
        return ResponseEntity.ok(postResponse);
    }
    @PutMapping
    public ResponseEntity<PostResponse> updatePost(@RequestBody UpdatePostResource resource){
        var post = postMapper.map(resource, Post.class);
        var updatePost = postService.update(post);
        if(updatePost.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        PostResponse postResponse = new PostResponse(updatePost.get());
        return ResponseEntity.ok(postResponse);
    }
    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId){
        postService.delete(postId);
        return ResponseEntity.noContent().build();
    }

}
