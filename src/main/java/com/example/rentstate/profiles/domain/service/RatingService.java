package com.example.rentstate.profiles.domain.service;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.model.entities.Rating;

import java.util.Optional;

public interface RatingService {
    Optional<Rating> create(Rating ratings);
    Optional<Rating> update(Rating ratings);

    Integer getAverageRatingByRatedUser(User ratedUser);

}