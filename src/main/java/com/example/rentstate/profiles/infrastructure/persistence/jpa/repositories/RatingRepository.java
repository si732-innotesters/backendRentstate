package com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.model.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findAllByRatedUser(User ratedUser);

    Optional<Rating> findByRatedUserAndRatedByUser(User ratedUser, User ratedByUser);
}