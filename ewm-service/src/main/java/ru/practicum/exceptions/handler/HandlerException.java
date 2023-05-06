package ru.practicum.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exceptions.exception.*;
import ru.practicum.exceptions.response.ErrorResponse;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
@ControllerAdvice
public class HandlerException {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exc(MethodArgumentNotValidException ex) {
        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST,
                        "Incorrectly made request.",
                        String.format("Field: %s. Error: %s. Value: %s",
                                Objects.requireNonNull(ex.getFieldError()).getField(),
                                ex.getFieldError().getDefaultMessage(),
                                ex.getFieldError().getRejectedValue()),
                        LocalDateTime.now().withNano(0)),
                HttpStatus.BAD_REQUEST);
        log.info(String.valueOf(responseEntity));
        return responseEntity;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exc(NotFoundException ex) {
        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND,
                        ex.getReason(),
                        ex.getMessage(),
                        LocalDateTime.now().withNano(0)),
                HttpStatus.NOT_FOUND);
        log.info(String.valueOf(responseEntity));
        return responseEntity;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exc(ConstraintViolationException ex) {
        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                new ErrorResponse(HttpStatus.CONFLICT,
                        "Integrity constraint has been violated.",
                        ex.getMessage(),
                        LocalDateTime.now().withNano(0)),
                HttpStatus.CONFLICT);
        log.info(String.valueOf(responseEntity));
        return responseEntity;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exc(PSQLException ex) {
        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                new ErrorResponse(HttpStatus.CONFLICT,
                        "Integrity constraint has been violated.",
                        ex.getMessage(),
                        LocalDateTime.now().withNano(0)),
                HttpStatus.CONFLICT);
        log.info(String.valueOf(responseEntity));
        return responseEntity;
    }

//    @ExceptionHandler(value = {StatusException.class, ConstraintForeignKeyException.class, AccessException.class,
//            TimeException.class, DuplicateException.class, InvalidRequestException.class,
//            HttpMessageNotReadableException.class})
//    public ResponseEntity<ErrorResponse> totalConflictExc(RuntimeException ex) {
//        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
//                new ErrorResponse(HttpStatus.CONFLICT,
//                        "Integrity constraint has been violated.",
//                        ex.getMessage(),
//                        LocalDateTime.now().withNano(0)),
//                HttpStatus.CONFLICT);
//        log.info(String.valueOf(responseEntity));
//        return responseEntity;
//    }

    @ExceptionHandler(value = {ConflictException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> totalConflictExc(RuntimeException ex) {
        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                new ErrorResponse(HttpStatus.CONFLICT,
                        "Integrity constraint has been violated.",
                        ex.getMessage(),
                        LocalDateTime.now().withNano(0)),
                HttpStatus.CONFLICT);
        log.info(String.valueOf(responseEntity));
        return responseEntity;
    }

}
