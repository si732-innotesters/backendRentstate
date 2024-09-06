package com.example.rentstate;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.model.valueobjects.Categories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class RealizarRentaPruebaIntegral {

    @Mock
    User renter;

    @Mock
    User author;

    Categories category = Categories.room;

    @InjectMocks
    Property property = new Property(
            "Departamento en el centro",
            "Departamento de 2 habitaciones",
            "2 habitaciones, 1 baño",
            "Centro",
            category,
            true,
            true,
            "url-imagen",
            null,
            author,
            100.0
    );

    @Test
    void puedeRentarConDineroUsuarioYPropiedad() {
        Mockito.when(renter.getName()).thenReturn("Roberto");
        Mockito.when(renter.getMoney()).thenReturn(70.0);
        Mockito.when(renter.getIsPremium()).thenReturn(true);

        boolean rentStatus = property.checkRentStatus();
        assertTrue(rentStatus);

        boolean fundsCheck = Property.renterHasFundsForPropertyRent(renter, property);
        assertTrue(fundsCheck);

        boolean result = property.rentProperty(renter, property.getAvailable());

        assertTrue(result);

        assertEquals(renter, property.getRenter());
    }
}