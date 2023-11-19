package com.example.rentstate.properties.shared.services.implementation;

import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.rentstate.properties.domain.model.entities.Comment;
import com.example.rentstate.properties.domain.service.CommentService;
import com.example.rentstate.properties.infraestructure.persistence.jpa.repositories.CommentRepository;
import com.example.rentstate.properties.infraestructure.persistence.jpa.repositories.PostRepository;
import com.example.rentstate.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    public final CommentRepository commentRepository;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Comment> getById(Long commentId) {
        return Optional.ofNullable(commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment", commentId)));
    }

    @Override
    public Optional<Comment> create(Comment comment) {
        return Optional.of(commentRepository.save(comment));
    }


    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        var comments = commentRepository.findAllByPostId(postId);
        return comments;

    }
}
