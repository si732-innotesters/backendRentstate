package com.example.rentstate.forums.shared.services.implementation;

import com.example.rentstate.forums.domain.model.aggregates.ForumAnswer;
import com.example.rentstate.forums.domain.service.ForumAnswerService;
import com.example.rentstate.forums.infraestructure.persistence.jpa.repositories.ForumAnswerRepository;
import com.example.rentstate.forums.infraestructure.persistence.jpa.repositories.ForumQuestionRepository;
import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.rentstate.shared.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumAnswerServiceImpl implements ForumAnswerService {
    private final ForumAnswerRepository forumAnswerRepository;

    private final ForumQuestionRepository forumQuestionRepository;
    private final UserRepository userRepository;



    public ForumAnswerServiceImpl(ForumAnswerRepository forumAnswerRepository, ForumQuestionRepository forumQuestionRepository, UserRepository userRepository) {
        this.forumAnswerRepository = forumAnswerRepository;
        this.forumQuestionRepository=forumQuestionRepository;
        this.userRepository=userRepository;
    }

    @Override
    public Optional<ForumAnswer> getById(Long forumAnswerId) {
        return Optional.ofNullable(forumAnswerRepository.findById(forumAnswerId)
                .orElseThrow(() -> new ResourceNotFoundException("Forum Answer", forumAnswerId)));
    }

    @Override
    public List<ForumAnswer> getAll() {
        return forumAnswerRepository.findAll();
    }

    @Override
    public Optional<ForumAnswer> create(ForumAnswer forumAnswer) {
    }


    @Override
    public Optional<ForumAnswer> update(ForumAnswer forumAnswer) {
        return Optional.ofNullable(forumAnswerRepository.findById(forumAnswer.getId()).map(forumAnswerToUpdate ->
                        forumAnswerRepository.save(forumAnswerToUpdate
                                .withContent(forumAnswer.getContent())
                        ))
                .orElseThrow(() -> new ResourceNotFoundException("Forum Answer", forumAnswer.getId())));
    }

    @Override
    public ResponseEntity<?> delete(Long forumAnswerId) {
        return forumAnswerRepository.findById(forumAnswerId).map(forumAnswer -> {
                    forumAnswerRepository.delete(forumAnswer);
                    return ResponseEntity.ok().build();})
                .orElseThrow(() -> new ResourceNotFoundException("Forum Answer", forumAnswerId));
    }
}
