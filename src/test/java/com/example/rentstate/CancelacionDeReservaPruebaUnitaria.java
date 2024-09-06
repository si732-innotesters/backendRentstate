package com.example.rentstate;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.model.valueobjects.Categories;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CancelacionDeReservaPruebaUnitaria {

    @Test
    void testCancelReservationWhenReservedByUser() {
        // Arrange
        User user = Mockito.mock(User.class);
        Mockito.when(user.getName()).thenReturn("Roberto");

        Property property = new Property(
                "Departamento en el centro",
                "Departamento de 2 habitaciones",
                "2 habitaciones, 1 baño",
                "Centro",
                Categories.room,
                true,
                true,
                "url-imagen",
                user,
                null,
                100.0
        );

        // Act
        boolean result = property.cancelReservation(user);

        // Assert
        assertTrue(result);
    }
}