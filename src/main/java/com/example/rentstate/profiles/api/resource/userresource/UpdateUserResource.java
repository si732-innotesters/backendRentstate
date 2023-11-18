package com.example.rentstate.profiles.api.resource.userresource;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UpdateUserResource {
    private Long id;
    private String name;
    private String lastName;
    private Integer age;
    private String gender;
    private String description;
    private Boolean isPremium;
    private String photoUrl;

    public UpdateUserResource(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.description = user.getDescription();
        this.isPremium = user.getIsPremium();
        this.photoUrl = user.getPhotoUrl();
    }
}