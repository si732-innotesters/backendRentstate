package com.example.rentstate.posts.api.resource;

import com.example.rentstate.posts.domain.model.entities.Property;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class PropertyResource {
    private Long Id;
    private String name;
    private String description;
    private String characteristics;
    private String location;
    private String category;
    private boolean available;
    private User renterId;
    private User authorId;

    public PropertyResource(Property property) {
        Id = property.getId();
        this.name = property.getName();
        this.description = property.getDescription();
        this.characteristics = property.getCharacteristics();
        this.location = property.getLocation();
        this.category = property.getCategory();
        this.available = property.getAvailable();
        this.renterId = property.getRenterId();
        this.authorId = property.getAuthorId();
    }
}
