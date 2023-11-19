package com.example.rentstate.properties.domain.model.entities;

import com.example.rentstate.properties.api.resource.postResource.PostResource;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
@Table(name ="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="property_id", nullable = false)
    private Property propertyId;

    @NotNull
    @NotBlank
    private String title;

    @Min(value = 0)
    @NotNull
    private Double price;

    public Post(Property propertyId, PostResource postResource) {
        this.propertyId = propertyId;
        this.title = postResource.getTitle();
        this.price = postResource.getPrice();
    }

}
