package com.example.rentstate.posts.api.resource;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UpdatePropertyResource {
    private Long Id;
    private String name;
    private String description;
    private String characteristics;
    private String location;
    private String category;
    private boolean available;
    private User renterId;
    private User authorId;
}
