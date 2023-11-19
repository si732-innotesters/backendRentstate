package com.example.rentstate.properties.api.rest;

import com.example.rentstate.profiles.domain.service.UserService;
import com.example.rentstate.properties.api.resource.reservationResource.ReservationResource;
import com.example.rentstate.properties.api.resource.reservationResource.ReservationResponse;
import com.example.rentstate.properties.domain.model.entities.Reservation;
import com.example.rentstate.properties.domain.service.PropertyService;
import com.example.rentstate.properties.domain.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/reservations", produces = "application/json")
public class ReservationController {
    private final ReservationService reservationService;
    private final PropertyService propertyService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService, PropertyService propertyService, UserService userService) {
        this.reservationService = reservationService;
        this.propertyService = propertyService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation( ReservationResource reservationResource){
        var propertyId = propertyService.getById(reservationResource.getPropertyId());
        var userId = userService.getById(reservationResource.getUserId());
        if(propertyId.isEmpty() || userId.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Reservation newReservation = new Reservation(userId.get(), propertyId.get());
        var reservation = reservationService.create(newReservation);
        if(reservation.isPresent()){
            var reservationResponse = new ReservationResponse(reservation.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationResponse);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @GetMapping("{reservationId}")
    public  ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long reservationId){
        var reservation = reservationService.getById(reservationId);
        var reservationResponse = new ReservationResponse(reservation.get());
        return ResponseEntity.ok(reservationResponse);
    }
    @GetMapping("/property/{propertyId}")
    public List<ReservationResponse> getAllByPropertyId(@PathVariable Long propertyId){
        var property = propertyService.getById(propertyId);
        if(property.isEmpty()){
            throw new IllegalArgumentException("Property does not exist");
        }
        List<Reservation> reservations = reservationService.getReservationsByPropertyId(property.get().getId());
        List<ReservationResponse> reservationResponseList = reservations.stream()
                .map(reservation-> new ReservationResponse(reservation))
                .collect(Collectors.toList());
        return reservationResponseList;
    }

}
