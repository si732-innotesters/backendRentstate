package com.example.rentstate.properties.api.resource.reservationResource;

import com.example.rentstate.properties.domain.model.entities.Reservation;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ReservationResponse {
    private Long reservationId;
    private Long userId;
    private Long propertyId;
    public ReservationResponse(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.userId = reservation.getUser().getId();
        this.propertyId = reservation.getProperty().getId();
    }
}
