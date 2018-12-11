package work.controller;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseMsg handleNotFoundException(EntityNotFoundException ex) {
        return new ResponseMsg(ex.getMessage());
    }
}
