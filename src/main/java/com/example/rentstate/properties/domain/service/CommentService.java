package com.example.rentstate.properties.domain.service;

import com.example.rentstate.properties.domain.model.entities.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {
    Optional<Comment> getById(Long commentId);
    Optional<Comment> create(Comment comment);
    List<Comment> getCommentsByPostId(Long postId);
}
