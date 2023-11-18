package com.example.rentstate.forums.infraestructure.persistence.jpa.repositories;


import com.example.rentstate.forums.domain.model.entities.ForumQuestion;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumQuestionRepository extends JpaRepository<ForumQuestion, Long> {

    List<ForumQuestion>findByAuthor (User author);

}
