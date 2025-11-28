package com.bookly.handler;

import com.bookly.exception.BadRequestException;
import com.bookly.exception.BadRequestExceptionDetails;
import com.bookly.exception.ResourceNotFoundException;
import com.bookly.exception.ResourceNotFoundExceptionDetails;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Requisição Inválida, Cheque a Documentação")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundExceptionDetails> handleResourceNotFoundException(ResourceNotFoundException rnfe) {
        return new ResponseEntity<>(
                ResourceNotFoundExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("Recurso Não Encontrado")
                        .details(rnfe.getMessage())
                        .developerMessage(rnfe.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException manvException) {

        String fieldsMessage = manvException.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Campos Inválidos - Verifique a requisição")
                        .details("Os campos a seguir estão incorretos: " + fieldsMessage)
                        .developerMessage(manvException.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
