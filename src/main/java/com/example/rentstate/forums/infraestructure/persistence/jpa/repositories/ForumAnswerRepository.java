package com.example.rentstate.forums.infraestructure.persistence.jpa.repositories;


import com.example.rentstate.forums.domain.model.entities.ForumAnswer;
import com.example.rentstate.forums.domain.model.entities.ForumQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumAnswerRepository extends JpaRepository<ForumAnswer,Long> {
    List<ForumAnswer> findAllByQuestion(ForumQuestion question);
}
