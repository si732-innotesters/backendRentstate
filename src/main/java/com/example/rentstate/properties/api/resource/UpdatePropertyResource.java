package com.example.rentstate.properties.api.resource;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.valueobjects.Categories;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UpdatePropertyResource {

    private Long id;
    private String name;
    private String description;
    private String characteristics;
    private String location;
    private Categories category;
    private boolean available;
    private Long renterId;
}
