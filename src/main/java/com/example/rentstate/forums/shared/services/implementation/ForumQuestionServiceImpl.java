package com.example.rentstate.forums.shared.services.implementation;

import com.example.rentstate.forums.domain.model.entities.ForumQuestion;
import com.example.rentstate.forums.domain.service.ForumQuestionService;
import com.example.rentstate.forums.infraestructure.persistence.jpa.repositories.ForumAnswerRepository;
import com.example.rentstate.forums.infraestructure.persistence.jpa.repositories.ForumQuestionRepository;
import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.rentstate.shared.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumQuestionServiceImpl implements ForumQuestionService {

    private final ForumQuestionRepository forumQuestionRepository;
    private final ForumAnswerRepository forumAnswerRepository;
    private final UserRepository userRepository;

    public ForumQuestionServiceImpl(ForumAnswerRepository forumAnswerRepository, ForumQuestionRepository forumQuestionRepository, UserRepository userRepository) {
        this.forumAnswerRepository = forumAnswerRepository;
        this.forumQuestionRepository=forumQuestionRepository;
        this.userRepository=userRepository;
    }

    @Override
    public Optional<ForumQuestion> getById(Long forumQuestionId) {
        return Optional.ofNullable(forumQuestionRepository.findById(forumQuestionId)
                .orElseThrow(() -> new ResourceNotFoundException("Forum Question", forumQuestionId)));
    }

    @Override
    public List<ForumQuestion> getAll() {
        return forumQuestionRepository.findAll();
    }

    @Override
    public Optional<ForumQuestion> create(ForumQuestion forumQuestion) {
        return Optional.of(forumQuestionRepository.save(forumQuestion));
    }


    @Override
    public Optional<ForumQuestion> update(ForumQuestion forumQuestion) {
        return Optional.ofNullable(forumQuestionRepository.findById(forumQuestion.getId())
                .map(forumQuestionToUpdate ->
                        forumQuestionRepository.save(forumQuestionToUpdate
                                .withQuestion(forumQuestion.getQuestion())
                        ))
                .orElseThrow(() -> new ResourceNotFoundException("Forum Question", forumQuestion.getId())));
    }

    @Override
    public ResponseEntity<?> delete(Long forumQuestionId) {
        return forumQuestionRepository.findById(forumQuestionId).map(forumQuestion -> {
                    forumQuestionRepository.delete(forumQuestion);
                    return ResponseEntity.ok().build();})
                .orElseThrow(() -> new ResourceNotFoundException("Forum Question", forumQuestionId));
    }
}
