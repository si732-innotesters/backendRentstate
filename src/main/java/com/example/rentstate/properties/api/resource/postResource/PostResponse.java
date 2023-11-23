package com.example.rentstate.properties.api.resource.postResource;

import com.example.rentstate.profiles.api.resource.userresource.ResponseUserResource;
import com.example.rentstate.properties.api.resource.propertyResource.ResponsePropertyResource;
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
    private String title;
    private Double price;
    private ResponsePropertyResource property;
    private ResponseUserResource author;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.property = new ResponsePropertyResource(post.getProperty());
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.author = new ResponseUserResource(post.getProperty().getAuthor());
    }
}
