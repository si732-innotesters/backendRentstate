package com.example.rentstate.properties.api.rest;

import com.example.rentstate.properties.api.resource.propertyResource.CreatePropertyResource;
import com.example.rentstate.properties.api.resource.propertyResource.ResponsePropertyResource;
import com.example.rentstate.properties.api.resource.propertyResource.UpdatePropertyResource;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.service.PostService;
import com.example.rentstate.properties.domain.service.PropertyService;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/properties", produces = "application/json")
public class PropertyController {
    private final PropertyService propertyService;
    private final UserService userService;
    private final PostService postService;


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

        List<Property> properties = propertyService.getAllByAuthor(author.get());

        List<ResponsePropertyResource> resourceList = properties.stream()
                .map(property -> new ResponsePropertyResource(property))
                .collect(Collectors.toList());
        return resourceList;
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ResponsePropertyResource> updateProperty(
            @RequestBody UpdatePropertyResource updatePropertyResource) {

        Optional<Property> updatedProperty = propertyService.getById(updatePropertyResource.getId());

        if(updatedProperty.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        updatedProperty.get().update(updatePropertyResource);

        Optional<Property> updated = propertyService.update(updatedProperty.get());

        ResponsePropertyResource propertyResource = new ResponsePropertyResource(updated.get());

        return ResponseEntity.ok(propertyResource);
    }

    @DeleteMapping("{propertyId}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long propertyId){
        propertyService.delete(propertyId);
        return ResponseEntity.noContent().build();
    }


    //reservations
    @PostMapping(value = "/reservation/property-id/{propertyId}/user-id/{userId}/{action}")
    public ResponseEntity<?> manageReservation(
            @PathVariable Long propertyId, @PathVariable Long userId, @PathVariable String action) {

        Optional<Property> property = propertyService.getById(propertyId);
        Optional<User> authorReserve = userService.getById(userId);

        if (authorReserve.isEmpty() || property.isEmpty())
            throw new IllegalArgumentException("Author or Property not found");

        if ("add".equalsIgnoreCase(action)) {
            propertyService.reserveProperty(property.get(), authorReserve.get());
        } else if ("remove".equalsIgnoreCase(action)) {
            propertyService.cancelReservation(property.get(), authorReserve.get());
        } else if ("accept".equalsIgnoreCase(action)){
            property.get().setRenter(authorReserve.get());
            property.get().setAvailable(false);

            property.get().getReservedByUsers().clear();

            propertyService.update(property.get());
            postService.deleteAllPostByProperty(property.get());
        }
        else{
            throw new IllegalArgumentException("Invalid action. Use 'add' or 'remove'.");
        }

        return ResponseEntity.ok().build();
    }


}
