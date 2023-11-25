package com.example.rentstate.properties.domain.model.entities;

import com.example.rentstate.properties.api.resource.postResource.CreatePostResource;
import com.example.rentstate.properties.api.resource.propertyResource.UpdatePropertyResource;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Property property;

    @Size(max = 100, message = "max 100 characters")
    @NotNull
    @NotBlank
    private String title;

    @Min(value = 100, message = "min value 100")
    @NotNull
    private Double price;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    public Post(Property property, CreatePostResource postResource) {
        this.property = property;
        this.title = postResource.getTitle();
        this.price = postResource.getPrice();
        this.createdAt = new Date();
    }



}
