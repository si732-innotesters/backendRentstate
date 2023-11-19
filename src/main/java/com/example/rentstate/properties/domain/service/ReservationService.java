package com.example.rentstate.properties.domain.service;

import com.example.rentstate.properties.domain.model.entities.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> getById(Long reservationId);
    Optional<Reservation> create(Reservation reservation);
    List<Reservation> getReservationsByPropertyId(Long propertyId);
}
