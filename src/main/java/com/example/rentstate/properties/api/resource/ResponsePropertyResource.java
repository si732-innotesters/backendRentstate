package com.example.rentstate.properties.api.resource;

import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.valueobjects.Categories;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ResponsePropertyResource {

    private Long Id;
    private String name;
    private String description;
    private String characteristics;
    private String location;
    private Categories category;
    private boolean available;
    private Long authorId;
    private Long renterId;

    public ResponsePropertyResource(Property property) {
        Id = property.getId();
        this.name = property.getName();
        this.description = property.getDescription();
        this.characteristics = property.getCharacteristics();
        this.location = property.getLocation();
        this.category = property.getCategory();
        this.available = property.getAvailable();
        this.authorId = property.getAuthor().getId();

        if (property.getRenter() != null) {
            this.renterId = property.getRenter().getId();
        }else{
            this.renterId = null;
        }
    }

}
