package upgrade.nicky.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import upgrade.nicky.reservation.Exception.ReservationDateIncorrectException;
import upgrade.nicky.reservation.Exception.ReservationNotFoundException;
import upgrade.nicky.reservation.model.Reservation;
import upgrade.nicky.reservation.repository.ReservationRepositoy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReservationService {

    private static int DAYS_IN_A_MONTH = 30;

    @Autowired
    ReservationRepositoy repository;

    public List<LocalDate> getAvailbility() {

        LocalDate today = LocalDate.now();
        List<Reservation> reservationList =  repository.getReservationList(today.plusDays(1));

        //create the availability list
        List<LocalDate> availabilityLst = new LinkedList<>();
        for (int i = 1; i <= DAYS_IN_A_MONTH ; i++) {
            availabilityLst.add(today.plusDays(i));
        }

        //remove the reserved date
        for (Reservation reservation : reservationList) {
            long period = ChronoUnit.DAYS.between(reservation.getArrival_date(), reservation.getDeparture_date());
            for (int i = 0 ; i < period ; i++) {
                availabilityLst.remove(reservation.getArrival_date().plusDays(i));
            }
        }

        return availabilityLst;
    }

    private void dateCheck(Reservation reservation) {
        //check if arrival date and departure date are at most 3 days apart
        long noOfDaysBetween = ChronoUnit.DAYS.between(reservation.getArrival_date(), reservation.getDeparture_date());
        if (noOfDaysBetween > 3) {
            throw new ReservationDateIncorrectException("maximum of 3 days can be reserved");
        }
        //check if both arrival date and departure date are within 1 month of today ( use 30 days = 1 month)
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate date_in_one_month = LocalDate.now().plusDays(DAYS_IN_A_MONTH);
        if (reservation.getArrival_date().compareTo(date_in_one_month) > 0 || reservation.getDeparture_date().compareTo(date_in_one_month) > 0 ||
                reservation.getArrival_date().compareTo(tomorrow) < 0 || reservation.getDeparture_date().compareTo(tomorrow) < 0) {
            throw new ReservationDateIncorrectException("campsite can only be reserved up to 1 month in advance");
        }
    }

    @Transactional (isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public Reservation reserve(Reservation reservation) {

        dateCheck(reservation);
        //check if dates are still available
        if (repository.findIfReservationExist(reservation.getId(), reservation.getArrival_date(), reservation.getDeparture_date()) == 0)
            return repository.save(reservation);
        else
            throw new ReservationDateIncorrectException("Dates not available");
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void modifyReservation(Integer id, Reservation reservation) {
        if (!repository.findById(id).isPresent()) {
            throw new ReservationNotFoundException(id);
        }
        else {
            reservation.setId(id);
            reserve(reservation);
        }
    }

    @Transactional
    public void deleteReservation(Integer id) {
        if (!repository.findById(id).isPresent()) {
            throw new ReservationNotFoundException(id);
        }
        else {
            repository.deleteById(id);

        }
    }
}
