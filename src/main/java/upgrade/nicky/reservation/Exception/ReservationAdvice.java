package upgrade.nicky.reservation.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ReservationAdvice {

    @ResponseBody
    @ExceptionHandler(ReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String reservationNotFoundHandler(ReservationNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ReservationDateIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String reservationDateIncorrectHandler(ReservationDateIncorrectException ex) {
        return ex.getMessage();
    }
}
