package com.example.rentstate;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.entities.Property;
import com.example.rentstate.properties.domain.model.valueobjects.Categories;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsuarioAutorCuentaConPropiedadDuplicada {
    @Test
    void whenUserHasDuplicateProperties_thenReturnTrue() {
        // Arrange
        User user = new User();
        user.setName("John Doe");

        List<Property> properties = new ArrayList<>();
        properties.add(new Property("Propiedad 1", "DescripcionPrueba", "CaracteriticasPruebas", "UbicacionPrueba", Categories.room, true, false, "url1", user, user, 100.0));
        properties.add(new Property("Propiedad 1", "DescripcionPrueba", "CaracteriticasPruebas", "UbicacionPrueba", Categories.room, true, false, "url2", user, user, 100.0));
        properties.add(new Property("Propiedad 2", "OtraDescripcion", "OtrasCaracteriticas", "OtraUbicacion", Categories.room, true, false, "url3", user, user, 100.0));

        // Act
        boolean result = Property.hasDuplicateProperties(user, properties);

        // Assert
        assertTrue(result);
    }
}

