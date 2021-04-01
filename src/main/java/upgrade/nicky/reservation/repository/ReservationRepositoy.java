package upgrade.nicky.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upgrade.nicky.reservation.model.Reservation;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepositoy extends JpaRepository<Reservation, Integer> {

    @Query("SELECT count(1) FROM Reservation r where Id != :Id and ((r.arrival_date <= :arrival_date AND r.departure_date >=  :departure_date) " +
            "OR (r.arrival_date >= :arrival_date AND r.departure_date <=  :departure_date) " +
            "OR (r.arrival_date < :departure_date AND r.departure_date >  :departure_date) " +
            "OR (r.arrival_date < :arrival_date AND r.departure_date >  :arrival_date))")
    public int findIfReservationExist(@Param("Id") int Id, @Param("arrival_date") LocalDate arrival_date, @Param("departure_date") LocalDate departure_date);

    @Query("SELECT r FROM Reservation r where r.departure_date > :arrival_date ")
    public List<Reservation> getReservationList(@Param("arrival_date") LocalDate arrival_date);
}
