package com.example.rentstate.profiles.api.rest;

import com.example.rentstate.profiles.api.resource.ratingsresource.CreateRatingResource;
import com.example.rentstate.profiles.api.resource.userresource.ResourceUserResponse;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.model.entities.Rating;
import com.example.rentstate.profiles.domain.service.RatingService;
import com.example.rentstate.profiles.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/ratings", produces = "application/json")
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;

    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addRating(@RequestBody CreateRatingResource createRatingResource) {

        Optional<User> ratedUser = userService.getById(createRatingResource.getRatedUserId());
        Optional<User> ratedByUser = userService.getById(createRatingResource.getRatedByUserId());

        if (ratedUser.isEmpty() || ratedByUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some of the users do not exist");
        }

        Rating newRating = new Rating(ratedUser.get(), ratedByUser.get(), createRatingResource.getRating());

        try {
            Optional<Rating> createdRating = ratingService.create(newRating);
            return createdRating.map(rating ->
                            ResponseEntity.status(HttpStatus.CREATED).body("Rating created successfully"))
                    .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create rating"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("average-rating/{userRaredId}")
    public ResponseEntity<Integer> getAverageRating(@PathVariable Long userRaredId) {
        Optional<User> ratedUser = userService.getById(userRaredId);

        if (ratedUser.isEmpty()) {
            throw new IllegalArgumentException("The user was not found");
        }

        Integer averageRating = ratingService.getAverageRatingByRatedUser(ratedUser.get());

        return ResponseEntity.ok(averageRating);
    }


}