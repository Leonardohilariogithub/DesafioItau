package com.desafioItau.exceptions.handle;

import com.desafioItau.exceptions.ClienteCpfException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TratamentoDefaultException {

    @ExceptionHandler(ClienteCpfException.class)
    public ResponseEntity<DefaultException> handle(ClienteCpfException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.NOT_ACCEPTABLE.value()); // nao ser aceito
        defaultException.setMsg(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultException> handle(MethodArgumentNotValidException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // informaçoes ta errada
        defaultException.setMsg(" zzzzzzzzzzzzzzzzzzzzzzzzz! ");
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }
}
