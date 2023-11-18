package com.example.rentstate.forums.infraestructure.persistence.jpa.repositories;


import com.example.rentstate.forums.domain.model.aggregates.ForumQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumQuestionRepository extends JpaRepository<ForumQuestion, Long> {

    List<ForumQuestion>findByForumQuestionId(Long forumQuestionId);
    List<ForumQuestion>findByAuthorId(Long authorId);

}
