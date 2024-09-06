package com.example.rentstate;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.model.valueobjects.Categories;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RealizarRentaPruebaIntegral {

    Categories category = Categories.room;

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
            new User("Autor", false, 0.0),
            100.0
    );

    @Test
    void puedeRentarConDineroUsuarioYPropiedad() {
        User renter = new User("Roberto", true, 70.0);

        boolean result = property.rentProperty(renter, property.getAvailable());
        assertTrue(result);

        assertEquals(renter, property.getRenter());
    }
}

