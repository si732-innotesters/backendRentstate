package com.example.rentstate.forums.infraestructure.persistence.jpa.repositories;


import com.example.rentstate.forums.domain.model.aggregates.ForumAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumAnswerRepository extends JpaRepository<ForumAnswer,Long> {
    List<ForumAnswer> findByForumAnswerId(Long forumAnswerId);
}
