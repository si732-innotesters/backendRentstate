package com.example.rentstate.properties.domain.model.entities;

import com.example.rentstate.properties.api.resource.propertyResource.CreatePropertyResource;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.api.resource.propertyResource.UpdatePropertyResource;
import com.example.rentstate.properties.domain.model.valueobjects.Categories;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private Boolean available = true;

    @NotNull
    private Boolean isPosted = false;

    private String urlImg;

    @OneToOne
    @JoinColumn(name = "renter_id", nullable = true)
    private User renter;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "property_reservations",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> reservedByUsers = new ArrayList<>();

    public Property(User author, User renter, CreatePropertyResource createPropertyResource) {
        this.name = createPropertyResource.getName();
        this.description = createPropertyResource.getDescription();
        this.characteristics = createPropertyResource.getCharacteristics();
        this.location = createPropertyResource.getLocation();
        this.category = createPropertyResource.getCategory();
        this.available = createPropertyResource.getAvailable();
        this.urlImg = createPropertyResource.getUrlImg();
        this.renter= renter;
        this.author = author;
    }

    public void update(UpdatePropertyResource resource){
        this.name = resource.getName();
        this.description = resource.getDescription();
        this.characteristics = resource.getCharacteristics();
        this.location = resource.getLocation();
        this.category = resource.getCategory();
        this.available = resource.getAvailable();
        this.urlImg = resource.getUrlImg();
    }

    public void checkRentStatus(boolean isPosted, boolean isAvailable) {
        if (isPosted && isAvailable) {
            System.out.println("The property can be rented.");
        } else if (isPosted && !isAvailable) {
            System.out.println("The property cannot be rented because someone else has rented it.");
        } else {
            System.out.println("The property is not posted.");
        }
    }

    public void setPostedStatus(boolean isPosted) {
        this.isPosted = isPosted;
    }

    public void setAvailableStatus(boolean isAvailable) {
        this.available = isAvailable;
    }

    public Boolean getIsPosted() {
        return isPosted;
    }

    public Boolean getAvailable() {
        return available;
    }
}