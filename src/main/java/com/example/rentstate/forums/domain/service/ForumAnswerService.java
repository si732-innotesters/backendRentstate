package com.example.rentstate.forums.domain.service;

import com.example.rentstate.forums.domain.model.aggregates.ForumAnswer;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ForumAnswerService {
    Optional<ForumAnswer> getById(Long forumAnswerId);
    List<ForumAnswer> getAll();
    Optional<ForumAnswer> create(ForumAnswer forumAnswer);
    Optional<ForumAnswer> update(ForumAnswer forumAnswer);
    ResponseEntity<?> delete(Long forumAnswerId);
}
