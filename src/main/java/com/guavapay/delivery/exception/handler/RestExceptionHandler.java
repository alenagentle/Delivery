package com.guavapay.delivery.exception.handler;

import com.guavapay.delivery.exception.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({FieldAlreadyTakenException.class,
            IncorrectCredentialsException.class,
            ItemAlreadyTakenException.class,
            OrderingAlreadyTakenException.class,
            DifferentAddressesException.class,
            NotEnoughProductException.class})
    protected ResponseEntity<ExceptionResponse> handleConflictException(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ExceptionResponse> handleUnvalidatedJwtException(BindException ex) {
        String message = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnvalidatedJwtException.class)
    protected ResponseEntity<ExceptionResponse> handleUnvalidatedJwtException(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SizeException.class)
    protected ResponseEntity<ExceptionResponse> handleSizeException(SizeException ex) {
        String message = ex.getMessage();
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = ex.getMessage();
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
