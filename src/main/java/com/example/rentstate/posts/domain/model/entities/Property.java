package com.example.rentstate.posts.domain.model.entities;

import com.example.rentstate.posts.api.resource.CreatePropertyResource;
import com.example.rentstate.profiles.domain.model.aggregates.User;
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
@Table(name = "Properties")
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
    @NotBlank
    @Size(max = 100)
    private String category;

    @NotNull
    private Boolean available;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private User renterId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User authorId;

    public Property(User authorId, CreatePropertyResource createPropertyResource, User renterId) {
        this.name = createPropertyResource.getName();
        this.description = createPropertyResource.getDescription();
        this.characteristics = createPropertyResource.getCharacteristics();
        this.location = createPropertyResource.getLocation();
        this.category = createPropertyResource.getCategory();
        this.available = createPropertyResource.isAvailable();
        this.renterId = renterId;
        this.authorId = authorId;
    }
}
