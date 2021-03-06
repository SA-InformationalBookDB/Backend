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
        if(exception.getError().getCode() == Errors.BAD_REQUEST.getCode()) {
            return new ResponseEntity<>(exception.getError(), HttpStatus.BAD_REQUEST);
        } else if(exception.getError().getCode() == Errors.NOT_FOUND.getCode()) {
            return new ResponseEntity<>(exception.getError(), HttpStatus.NOT_FOUND);
        } else if(exception.getError().getCode() == Errors.UNAUTHORIZED.getCode()) {
            return new ResponseEntity<>(exception.getError(), HttpStatus.UNAUTHORIZED);
        } else if(exception.getError().getCode() == Errors.FORBIDDEN.getCode()) {
            return new ResponseEntity<>(exception.getError(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(exception.getError(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
