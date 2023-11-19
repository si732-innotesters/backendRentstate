package com.example.rentstate.properties.api.rest;

import com.example.rentstate.profiles.domain.service.UserService;
import com.example.rentstate.properties.api.resource.commentResource.CommentResource;
import com.example.rentstate.properties.api.resource.commentResource.CommentResponse;
import com.example.rentstate.properties.domain.model.entities.Comment;
import com.example.rentstate.properties.domain.service.CommentService;
import com.example.rentstate.properties.domain.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/comments", produces = "application/json")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;
    public CommentController(CommentService commentService, UserService userService, PostService postService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentResource commentResource){
        var postId = postService.getById(commentResource.getPostId());
        var userId = userService.getById(commentResource.getUserId());
        if(postId.isEmpty() || userId.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Comment newComment = new Comment(userId.get(), postId.get(), commentResource);
        var comment = commentService.create(newComment);
        if(comment.isPresent()){
            var commentResponse = new CommentResponse(comment.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("{commentId}")
    public  ResponseEntity<CommentResponse> getPostById(@PathVariable Long commentId){
        var comment = commentService.getById(commentId);
        var commentResponse = new CommentResponse(comment.get());
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/post/{postId}")
    public List<CommentResponse> getAllByPostId(@PathVariable Long postId){
        var post = postService.getById(postId);
        if(post.isEmpty()){
            throw new IllegalArgumentException("Post does not exist");
        }
        List<Comment> comments = commentService.getCommentsByPostId(post.get().getId());
        List<CommentResponse> commentResponseList = comments.stream()
                .map(comment-> new CommentResponse(comment))
                .collect(Collectors.toList());
        return commentResponseList;
    }
}