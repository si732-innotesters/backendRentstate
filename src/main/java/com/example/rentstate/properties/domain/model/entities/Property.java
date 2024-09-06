package com.example.rentstate.properties.domain.model.entities;

import com.example.rentstate.properties.api.resource.propertyResource.CreatePropertyResource;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.api.resource.propertyResource.UpdatePropertyResource;
import com.example.rentstate.properties.domain.model.valueobjects.Categories;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 400)
    private String description;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String characteristics;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String location;

    @NotNull
    private Categories category;

    @NotNull
    private double price;

    @NotNull
    private Boolean available = true;

    @NotNull
    private Boolean isPosted = false;

    private String urlImg;

    @OneToOne
    @JoinColumn(name = "renter_id", nullable = true)
    private User renter;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "property_reservations",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> reservedByUsers = new ArrayList<>();

    public Property(User author, User renter, CreatePropertyResource createPropertyResource) {
        this.name = createPropertyResource.getName();
        this.description = createPropertyResource.getDescription();
        this.characteristics = createPropertyResource.getCharacteristics();
        this.location = createPropertyResource.getLocation();
        this.category = createPropertyResource.getCategory();
        this.available = createPropertyResource.getAvailable();
        this.urlImg = createPropertyResource.getUrlImg();
        this.renter= renter;
        this.author = author;
    }

    public Property(String name, String description, String characteristics, String location, Categories category, Boolean available, Boolean isPosted, String urlImg, User renter, User author, double price) {
        this.name = name;
        this.description = description;
        this.characteristics = characteristics;
        this.location = location;
        this.category = category;
        this.available = available;
        this.isPosted = isPosted;
        this.urlImg = urlImg;
        this.renter = renter;
        this.author = author;
        this.price = price;
    }

    public void update(UpdatePropertyResource resource){
        this.name = resource.getName();
        this.description = resource.getDescription();
        this.characteristics = resource.getCharacteristics();
        this.location = resource.getLocation();
        this.category = resource.getCategory();
        this.available = resource.getAvailable();
        this.urlImg = resource.getUrlImg();
    }

    public boolean checkRentStatus() {
        if (this.isPosted && this.available) {
            System.out.println("La propiedad puede ser rentada");
            return true;
        } else if (this.isPosted && !this.available) {
            System.out.println("La propiedad no puede ser rentada porque alguien mas ya la está ocupando.");
            return false;
        } else {
            System.out.println("La propiedad no está publicada.");
            return false;
        }
    }

    public static boolean hasDuplicateProperties(User user, List<Property> properties) {
        for (int i = 0; i < properties.size(); i++) {
            Property property1 = properties.get(i);
            if (property1.getAuthor().equals(user)) {
                for (int j = i + 1; j < properties.size(); j++) {
                    Property property2 = properties.get(j);
                    if (property2.getAuthor().equals(user) &&
                            property1.getName().equals(property2.getName()) &&
                            property1.getDescription().equals(property2.getDescription()) &&
                            property1.getLocation().equals(property2.getLocation())) {
                        System.out.println("Revisar al usuario autor: " + user.getName());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean rentProperty(User newRenter, boolean availableProperty) {
        if (!availableProperty) {
            System.out.println("No se puede alquilar la propiedad porque no está disponible para alquilar.");
            return false;
        }

        if (!renterHasFundsForPropertyRent(newRenter, this)) {
            System.out.println("El inquilino no tiene fondos suficientes para alquilar la propiedad.");
            return false;
        }

        this.renter = newRenter;
        this.available = false;
        System.out.println("La propiedad ha sido alquilada a " + newRenter.getName() + ".");
        return true;
    }

    public static boolean renterHasFundsForPropertyRent(User renter, Property property) {
        if (!property.checkRentStatus()) {
            return false;
        }

        double propertyPrice = property.getPrice();
        double discountFactor = 1;

        if (renter.getIsPremium()) {
            discountFactor = 0.70;
        }
        double finalPrice = propertyPrice * discountFactor;

        if (renter.getMoney() >= finalPrice) {
            System.out.println(renter.getName() + " tiene suficiente dinero para alquilar la propiedad.");
            return true;
        } else {
            System.out.println(renter.getName() + " no tiene suficiente dinero para alquilar la propiedad.");
            return false;
        }
    }
    private boolean hasSufficientFunds(User renter) {
        return renterHasFundsForPropertyRent(renter, this);
    }
}