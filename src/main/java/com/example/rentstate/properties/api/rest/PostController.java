package com.example.rentstate.properties.api.rest;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.service.UserService;
import com.example.rentstate.properties.api.resource.commentResource.CommentResponse;
import com.example.rentstate.properties.api.resource.commentResource.CreateCommentResource;
import com.example.rentstate.properties.api.resource.postResource.CreatePostResource;
import com.example.rentstate.properties.api.resource.postResource.PostResponse;
import com.example.rentstate.properties.api.resource.postResource.UpdatePostResource;
import com.example.rentstate.properties.domain.model.entities.Comment;
import com.example.rentstate.properties.domain.model.entities.Post;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.service.CommentService;
import com.example.rentstate.properties.domain.service.PostService;
import com.example.rentstate.properties.domain.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200","https://renstate2-0.web.app"})
@RestController
@RequestMapping(value = "/api/v1/posts", produces = "application/json")
public class PostController {
    private final PostService postService;
    private final PropertyService propertyService;
    private final CommentService commentService;
    private final UserService userService;


    public PostController(PostService postService, PropertyService propertyService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.propertyService = propertyService;
        this.commentService = commentService;
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostResource createPostResource ){
        Optional<Property> property = propertyService.getById(createPostResource.getPropertyId());

        if(property.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        Post newPost = new Post(property.get(),createPostResource);
        var post = postService.create(newPost);

        if(post.isPresent()){
            var postResponse = new PostResponse(post.get());
            property.get().setIsPosted(true);
            propertyService.update(property.get());
            propertyService.update(property.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping()
    public ResponseEntity<List<PostResponse>> getAll() {
        List<Post> posts = postService.getAllPosts();

        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        List<Post> premiumPosts = posts.stream()
                .filter(post -> post.getProperty().getAuthor().getIsPremium())
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());

        posts.removeAll(premiumPosts);
        premiumPosts.addAll(posts);

        List<PostResponse> responseList = premiumPosts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
    @GetMapping("{postId}")
    public  ResponseEntity<PostResponse> getPostById(@PathVariable Long postId){
        var post = postService.getById(postId);
        var postResponse = new PostResponse(post.get());
        return ResponseEntity.ok(postResponse);
    }
    @GetMapping("author-id/{authorId}")
    public  List<PostResponse> getPostByAuthorId(@PathVariable Long authorId){
        List<Post> posts = postService.getAllPostsByAuthor(userService.getById(authorId).get());
        List<PostResponse>postResponseList = posts.stream()
                .map(post -> new PostResponse(post)).collect(Collectors.toList());

        return  postResponseList;
    }

    @PutMapping
    public ResponseEntity<PostResponse> updatePost(@RequestBody UpdatePostResource updatePostResource){

        var updatePost = postService.update(updatePostResource);

        if(updatePost.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        PostResponse postResponse = new PostResponse(updatePost.get());
        return ResponseEntity.ok(postResponse);
    }
    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId){
        Property property = postService.getById(postId).get().getProperty();
        property.setIsPosted(false);
        propertyService.update(property);
        postService.delete(postId);

        return ResponseEntity.noContent().build();
    }

    //comments section
    @PostMapping("/add-comment")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CreateCommentResource createCommentResource){
        Optional<User> author = userService.getById(createCommentResource.getAuthorId());
        Optional<Post> post = postService.getById(createCommentResource.getPostId());

        if(author.isEmpty() || post.isEmpty())
            throw new IllegalArgumentException("author or post not found");

        Comment newComment = new Comment(author.get(), post.get(), createCommentResource);
        Optional<Comment> comment = commentService.create(newComment);

        if(comment.isPresent()){
            CommentResponse commentResponse = new CommentResponse(comment.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/comment/post-id/{postId}")
    public List<CommentResponse> getAllCommentByPostId(@PathVariable Long postId){
        Optional<Post> post = postService.getById(postId);

        if(post.isEmpty())
            throw new IllegalArgumentException("Post not found");

        List<Comment>comments = commentService.getCommentsByPost(post.get());

        List<CommentResponse> responseList = comments.stream()
                .map(comment -> new CommentResponse(comment)).collect(Collectors.toList());

        return  responseList;
    }
}
