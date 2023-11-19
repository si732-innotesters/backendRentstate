package com.example.rentstate.properties.api.resource.postResource;

import com.example.rentstate.properties.domain.model.entities.Post;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private Long propertyId;
    private String title;
    private Double price;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.propertyId = post.getPropertyId().getId();
        this.title = post.getTitle();
        this.price = post.getPrice();
    }
}
