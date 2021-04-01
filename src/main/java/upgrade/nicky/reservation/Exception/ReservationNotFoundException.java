package upgrade.nicky.reservation.Exception;



public class ReservationNotFoundException extends RuntimeException{

    public ReservationNotFoundException(Integer Id) {
        super("Could not find reservation with ID " + Id );
    }

}
