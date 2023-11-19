package com.example.rentstate.properties.api.resource.postResource;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PostResource {
    private Long propertyId;
    private String title;
    private Double price;

}
