package com.example.rentstate.properties.api.resource.reservationResource;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ReservationResource {
    private Long userId;
    private Long propertyId;
}
