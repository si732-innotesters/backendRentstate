package com.example.rentstate.profiles.domain.model.aggregates;

import com.example.rentstate.profiles.api.resource.userresource.CreateUserResource;
import com.example.rentstate.profiles.api.resource.userresource.UpdateUserResource;
import com.example.rentstate.profiles.domain.model.entities.Rating;
import com.example.rentstate.profiles.domain.model.valueobjects.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2, max=50, message = "The name must have at least 2 characters and a maximum of 50")
    private String name;

    @NotNull
    @Size(min=2, max=50, message = "The last name must have at least 2 characters and a maximum of 50")
    private String lastName;

    @Embedded
    private Account account;

    @NotNull
    private Integer age = 18;

    @NotNull
    private String gender="reserved";

    @NotNull
    private String description = "Here you can write a description about yourself.";

    @NotNull
    private Boolean isPremium = false;

    private String photoUrl = "";

    public User(CreateUserResource resource){
        name = resource.getName();
        lastName = resource.getLastName();
        account = new Account(resource.getEmail(), resource.getPassword());
    }
    public void updateUser(UpdateUserResource resource){
        this.name = resource.getName();
        this.lastName = resource.getLastName();
        this.age = resource.getAge();
        this.gender = resource.getGender();
        this.description = resource.getDescription();
        this.isPremium = resource.getIsPremium();
        this.photoUrl = resource.getPhotoUrl();
    }

}