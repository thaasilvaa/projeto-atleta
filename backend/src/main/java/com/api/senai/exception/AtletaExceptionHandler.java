package com.api.senai.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AtletaExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>>
    tratarRuntimeException(RuntimeException ex) {

        Map<String, String> resposta =
                new HashMap<>();

        resposta.put(
            "erro",
            ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }

    @ExceptionHandler(
        MethodArgumentNotValidException.class
    )
    public ResponseEntity<Map<String, String>>
    tratarValidacao(
        MethodArgumentNotValidException ex) {

        Map<String, String> erros =
                new HashMap<>();

        ex.getBindingResult()
            .getFieldErrors()
            .forEach(error ->
                erros.put(
                    error.getField(),
                    error.getDefaultMessage()
                )
            );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erros);
    }

    @ExceptionHandler(
        DataIntegrityViolationException.class
    )
    public ResponseEntity<Map<String, String>>
    tratarDuplicidade(
        DataIntegrityViolationException ex) {

        Map<String, String> resposta =
                new HashMap<>();

        resposta.put(
            "erro",
            "Já existe um atleta com esse nome nessa modalidade."
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }
}