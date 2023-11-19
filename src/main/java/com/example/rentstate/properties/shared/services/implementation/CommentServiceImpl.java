package com.example.rentstate.properties.shared.services.implementation;

import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.rentstate.properties.domain.model.entities.Comment;
import com.example.rentstate.properties.domain.model.entities.Post;
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

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Comment> create(Comment comment) {
        return Optional.of(commentRepository.save(comment));
    }

    @Override
    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }
}
