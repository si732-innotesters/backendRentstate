package com.example.rentstate.properties.domain.service;

import com.example.rentstate.properties.domain.model.entities.Comment;
import com.example.rentstate.properties.domain.model.entities.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {
    Optional<Comment> create(Comment comment);
    List<Comment> getCommentsByPost(Post post);
}
