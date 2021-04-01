package upgrade.nicky.reservation.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "Reservation")
public class Reservation {

    @Id
    @GeneratedValue
    private int Id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String email;

    @Column
    private LocalDate arrival_date;

    @Column
    private LocalDate departure_date;

    public Reservation() {

    }

    public Reservation(String first_name, String last_name, String email, LocalDate arrival_date, LocalDate departure_date) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(LocalDate arrival_date) {
        this.arrival_date = arrival_date;
    }

    public LocalDate getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(LocalDate departure_date) {
        this.departure_date = departure_date;
    }
}
