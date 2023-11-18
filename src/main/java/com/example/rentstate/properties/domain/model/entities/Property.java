package com.example.rentstate.properties.domain.model.entities;

import com.example.rentstate.properties.api.resource.CreatePropertyResource;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.valueobjects.Categories;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 400)
    private String description;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String characteristics;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String location;

    @NotNull
    private Categories category;

    @NotNull
    private Boolean available;

    private String urlImg;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = true)
    private User renter;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Property(User author, User renter, CreatePropertyResource createPropertyResource) {
        this.name = createPropertyResource.getName();
        this.description = createPropertyResource.getDescription();
        this.characteristics = createPropertyResource.getCharacteristics();
        this.location = createPropertyResource.getLocation();
        this.category = createPropertyResource.getCategory();
        this.available = createPropertyResource.isAvailable();
        this.urlImg = createPropertyResource.getUrlImg();
        this.renter= renter;
        this.author = author;
    }

}
