package com.example.rentstate.posts.api.rest;

import com.example.rentstate.forums.api.resource.forumQuestionResource.ResourceForumQuestionResponse;
import com.example.rentstate.forums.domain.model.entities.ForumQuestion;
import com.example.rentstate.posts.api.resource.CreatePropertyResource;
import com.example.rentstate.posts.api.resource.PropertyResource;
import com.example.rentstate.posts.domain.model.entities.Property;
import com.example.rentstate.posts.domain.service.PropertyService;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/properties", produces = "application/json")
public class PropertyController {
    private final PropertyService propertyService;
    private final UserService userService;

    public PropertyController(PropertyService propertyService, UserService userService) {
        this.propertyService = propertyService;
        this.userService = userService;
    }

    @GetMapping
    public List<PropertyResource> getAllProperties(){
        List<Property> properties = propertyService.getAll();

        List<PropertyResource> propertyResources = properties.stream()
                .map(property -> new PropertyResource(property))
                .collect(Collectors.toList());

        return propertyResources;
    }

    @GetMapping("{propertyId}")
    public ResponseEntity<PropertyResource> getPropertiesById(@PathVariable Long propertyId){
        Optional<Property> property = propertyService.getById(propertyId);

        if(property.isEmpty())
            throw new IllegalArgumentException("Question with id " +propertyId+" does not exist");

        PropertyResource propertyResource = new PropertyResource(property.get());

        return ResponseEntity.ok(propertyResource);
    }

    @GetMapping({"/author/{authorId}"})
    public List<PropertyResource> getAllPropertiesByAuthorId(@PathVariable Long authorId){
        Optional<User> author = userService.getById(authorId);
        if (author.isEmpty()){
            throw new IllegalArgumentException("The property does not exist");
        }

        List<Property> properties = propertyService.getByAuthorId(author.get());

        List<PropertyResource> resourceList = properties.stream()
                .map(property -> new PropertyResource(property))
                .collect(Collectors.toList());
        return resourceList;
    }

    @PostMapping
    public ResponseEntity<PropertyResource> addProperty(
            @RequestBody CreatePropertyResource createPropertyResource){
        Optional<User> author = userService.getById(createPropertyResource.getAuthorId().getId());
        Optional<User> renter = userService.getById(createPropertyResource.getRenterId().getId());

        if (author.isEmpty())
            throw new IllegalArgumentException("The property does not exist");

        if (renter.isEmpty())
            throw new IllegalArgumentException("The renter does not exist");

        Property newProperty = new Property(author.get(), createPropertyResource, renter.get());

        Optional<Property> property = propertyService.create(newProperty);

        if (property.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new PropertyResource(property.get()));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{propertyId}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long propertyId){
        propertyService.delete(propertyId);
        return ResponseEntity.noContent().build();
    }


}
