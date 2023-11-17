package com.example.rentstate.profiles.api.resource.ratingsresource;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateRatingResource {

    private Long ratedUserId;

    private Long ratedByUserId;

    private int rating;

}
