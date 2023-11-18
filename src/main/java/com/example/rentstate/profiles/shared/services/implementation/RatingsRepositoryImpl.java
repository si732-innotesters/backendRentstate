package com.example.rentstate.profiles.shared.services.implementation;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.model.entities.Rating;
import com.example.rentstate.profiles.domain.service.RatingService;
import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.RatingRepository;
import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingsRepositoryImpl implements RatingService {

    private final RatingRepository ratingsRepository;
    private final UserRepository userRepository;

    public RatingsRepositoryImpl(RatingRepository ratingsRepository, UserRepository userRepository) {
        this.ratingsRepository = ratingsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Rating> create(Rating ratings) {
        Optional<User> ratedUser = userRepository.findById(ratings.getRatedUser().getId());
        Optional<User> ratedByUser = userRepository.findById(ratings.getRatedByUser().getId());

        if (ratedUser.isEmpty() || ratedByUser.isEmpty()) {
            throw new IllegalArgumentException("Some of the users do not exist");
        }

        if (ratings.getRating() < 1 || ratings.getRating() > 5) {
            throw new IllegalArgumentException("Invalid rating value");
        }

        Optional<Rating> existingRating = ratingsRepository. findByRatedUserAndRatedByUser(
                ratings.getRatedUser(), ratings.getRatedByUser());

        if (existingRating.isPresent()) {
            Rating updatedRating = existingRating.get();
            updatedRating.setRating(ratings.getRating());
            return update(updatedRating);
        } else {
            return Optional.of(ratingsRepository.save(ratings));
        }
    }


    @Override
    public Optional<Rating> update(Rating updatedRatings) {
        Optional<Rating> existingRating = ratingsRepository.findById(updatedRatings.getId());

        if (existingRating.isEmpty()) {
            throw new IllegalArgumentException("Rating with ID " + updatedRatings.getId() + " does not exist");
        }

        existingRating.get().setRating(updatedRatings.getRating());

        return Optional.of(ratingsRepository.save(existingRating.get()));
    }


    @Override
    public Integer getAverageRatingByRatedUser(User ratedUser) {
        List<Rating> ratingsList = ratingsRepository.findAllByRatedUser(ratedUser);

        if (ratingsList.isEmpty()) {
            return 5;
        }

        double averageRating = ratingsList.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(5.0);

        return (int) Math.round(averageRating);
    }
}