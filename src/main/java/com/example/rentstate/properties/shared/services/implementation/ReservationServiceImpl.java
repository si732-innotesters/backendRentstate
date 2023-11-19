package com.example.rentstate.properties.shared.services.implementation;

import com.example.rentstate.properties.domain.model.entities.Reservation;
import com.example.rentstate.properties.domain.service.ReservationService;
import com.example.rentstate.properties.infraestructure.persistence.jpa.repositories.ReservationRepository;
import com.example.rentstate.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Optional<Reservation> getById(Long reservationId) {
        return Optional.ofNullable(reservationRepository.findById(reservationId)
                .orElseThrow(()->new ResourceNotFoundException("Reservation", reservationId)));
    }

    @Override
    public Optional<Reservation> create(Reservation reservation) {
        if (reservation.getProperty().getAvailable() != null && reservation.getProperty().getAvailable()) {
            if(reservation.getProperty().getAuthor().equals(reservation.getUser())){
                throw new IllegalArgumentException("Authors cannot reserve their own properties");
            }
            return Optional.of(reservationRepository.save(reservation));
        } else {
            throw new IllegalArgumentException("Property is not available");
        }
    }

    @Override
    public List<Reservation> getReservationsByPropertyId(Long propertyId) {
        var reservations = reservationRepository.findAllByPropertyId(propertyId);
        return reservations;
    }
}
