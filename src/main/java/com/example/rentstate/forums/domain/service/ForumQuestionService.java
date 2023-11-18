package com.example.rentstate.forums.domain.service;

import com.example.rentstate.forums.domain.model.entities.ForumQuestion;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ForumQuestionService {
    Optional<ForumQuestion> getById(Long forumQuestionId);
    List<ForumQuestion> getAll();
    Optional<ForumQuestion> create(ForumQuestion forumQuestion);
    Optional<ForumQuestion> update(ForumQuestion forumQuestion);
    ResponseEntity<?> delete(Long forumQuestionId);
}
