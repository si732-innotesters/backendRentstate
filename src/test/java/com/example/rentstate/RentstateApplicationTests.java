package com.example.rentstate;

import com.example.rentstate.properties.domain.model.entities.Property;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RentstateApplicationTests {

    @Test
    void whenPropertyIsPostedAndNotAvailable_thenCannotBeRented() {
        // Arrange
        Property property = new Property();
        property.setPostedStatus(true);
        property.setAvailableStatus(false);

        // Act
        boolean isPosted = property.getIsPosted();
        boolean isAvailable = property.getAvailable();
        property.checkRentStatus(isPosted, isAvailable);

        // Assert
        assertFalse(isAvailable, "The property cannot be rented because someone else has rented it.");
    }
}