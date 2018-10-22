package hu.bme.szarch.ibdb.error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class IbdbExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public Object handleServerException(ServerException exception, WebRequest request) {
        if(exception.getError().getCode() == 1) {
            return new ResponseEntity<>(exception.getError(), HttpStatus.BAD_REQUEST);
        } else if(exception.getError().getCode() == 2) {
            return new ResponseEntity<>(exception.getError(), HttpStatus.NOT_FOUND);
        } else if(exception.getError().getCode() == 3) {
            return new ResponseEntity<>(exception.getError(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(exception.getError(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
