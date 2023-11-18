package com.example.rentstate.forums.domain.service;

import com.example.rentstate.forums.domain.model.entities.ForumAnswer;
import com.example.rentstate.forums.domain.model.entities.ForumQuestion;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ForumAnswerService {
    Optional<ForumAnswer> create(ForumAnswer forumAnswer);

    List<ForumAnswer> findAllByQuestion(ForumQuestion question);
    ResponseEntity<?> delete(Long forumAnswerId);

}
