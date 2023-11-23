package com.example.rentstate.properties.api.resource.propertyResource;

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
    private String urlImg;
    private Boolean available;
}
