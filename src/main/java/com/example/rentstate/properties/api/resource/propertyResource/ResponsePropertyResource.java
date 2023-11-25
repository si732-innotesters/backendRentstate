package com.example.rentstate.properties.api.resource.propertyResource;

import com.example.rentstate.profiles.api.resource.userresource.ResponseUserResource;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.model.valueobjects.Categories;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Getter
@NoArgsConstructor
public class ResponsePropertyResource {

    private Long id;
    private String name;
    private String description;
    private String characteristics;
    private String location;
    private Categories category;
    private Boolean available;
    private Boolean isPosted;
    private Long authorId;
    private Long renterId;
    private String urlImg;
    private List<ResponseUserResource> reservedUsers;

    public ResponsePropertyResource(Property property) {
        this.id = property.getId();
        this.name = property.getName();
        this.description = property.getDescription();
        this.characteristics = property.getCharacteristics();
        this.location = property.getLocation();
        this.category = property.getCategory();
        this.available = property.getAvailable();
        this.isPosted = property.getIsPosted();
        this.urlImg = property.getUrlImg();
        this.authorId = property.getAuthor().getId();

        this.reservedUsers = property.getReservedByUsers().stream()
                .map(user -> new ResponseUserResource(user))
                .collect(Collectors.toList());


        if (property.getRenter() != null) {
            this.renterId = property.getRenter().getId();
        } else {
            this.renterId = null;
        }
    }

    public ResponsePropertyResource setReservedUsers(List<ResponseUserResource> reservedUsers) {
        this.reservedUsers = reservedUsers;
        return this;
    }
}
