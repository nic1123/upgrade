package upgrade.nicky.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upgrade.nicky.reservation.Exception.ReservationNotFoundException;
import upgrade.nicky.reservation.model.Reservation;
import upgrade.nicky.reservation.service.ReservationService;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/campsite/reservation")
public class ReservationController {

    @Autowired
    ReservationService service;

    @RequestMapping("/availibility")
    public List<LocalDate> getAvailibility() {

       return service.getAvailbility();
    }

    @PostMapping()
    public Integer reserve(@RequestBody Reservation reservation) {

        Reservation savedReservation = service.reserve(reservation);
        return savedReservation.getId();

    }

    @PutMapping("/{reservationId}")
    public String modifyReservation(@PathVariable("reservationId") Integer reservationId, @RequestBody Reservation reservation) {
        service.modifyReservation(reservationId, reservation);

        return "Reservation is modified";
    }

    @DeleteMapping("/{reservationId}")
    public String deleteReservation(@PathVariable("reservationId") Integer reservationId) {
        service.deleteReservation(reservationId);

        return "Reservation is deleted";
    }
}
