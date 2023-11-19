package com.example.rentstate.properties.api.rest;

import com.example.rentstate.properties.api.resource.propertyResource.CreatePropertyResource;
import com.example.rentstate.properties.api.resource.propertyResource.ResponsePropertyResource;
import com.example.rentstate.properties.api.resource.propertyResource.UpdatePropertyResource;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.service.PropertyService;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ResponsePropertyResource> addProperty(
            @RequestBody CreatePropertyResource createPropertyResource){
        Optional<User> author = userService.getById(createPropertyResource.getAuthorId());

        if (author.isEmpty())
            throw new IllegalArgumentException("The author does not exist");


        Property newProperty = new Property(author.get(), null, createPropertyResource);
        Optional<Property> property = propertyService.create(newProperty);

        if (property.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponsePropertyResource(property.get()));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{propertyId}")
    public ResponseEntity<ResponsePropertyResource> getPropertyById(@PathVariable Long propertyId){
        Optional<Property> property = propertyService.getById(propertyId);

        if(property.isEmpty())
            throw new IllegalArgumentException("Question with id " +propertyId+" does not exist");

        ResponsePropertyResource propertyResource = new ResponsePropertyResource(property.get());

        return ResponseEntity.ok(propertyResource);
    }

    @GetMapping({"/author/{authorId}"})
    public List<ResponsePropertyResource> getAllPropertiesByAuthorId(@PathVariable Long authorId){
        Optional<User> author = userService.getById(authorId);
        if (author.isEmpty()){
            throw new IllegalArgumentException("The author does not exist");
        }

        List<Property> properties = propertyService.getByAuthor(author.get());

        List<ResponsePropertyResource> resourceList = properties.stream()
                .map(property -> new ResponsePropertyResource(property))
                .collect(Collectors.toList());
        return resourceList;
    }

    @PutMapping
    public ResponseEntity<ResponsePropertyResource> updateUser(
            @RequestBody UpdatePropertyResource updatePropertyResource) {

        Optional<Property> updatedProperty = propertyService.update(updatePropertyResource);

        if(updatedProperty.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponsePropertyResource propertyResource = new ResponsePropertyResource(updatedProperty.get());
        return ResponseEntity.ok(propertyResource);
    }

    @DeleteMapping("{propertyId}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long propertyId){
        propertyService.delete(propertyId);
        return ResponseEntity.noContent().build();
    }


}
