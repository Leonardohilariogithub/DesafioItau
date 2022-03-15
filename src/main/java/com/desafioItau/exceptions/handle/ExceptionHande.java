package com.desafioItau.exceptions.handle;

import com.desafioItau.exceptions.ClienteCpfException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHande {

    @ExceptionHandler(ClienteCpfException.class)
    public ResponseEntity<DefaultException> handle(ClienteCpfException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value());
        defaultException.setMsg(" Documento Invalido ");
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }
}
