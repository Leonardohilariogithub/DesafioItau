package com.desafioItau.exceptions.handle;

import com.desafioItau.exceptions.ClienteCpfException;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.exceptions.HttpMessageNotReadableException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ClienteExistenteException.class)
    public ResponseEntity<DefaultException> handle(ClienteExistenteException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem("Documento ja cadastrado!");
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultException> handle(MethodArgumentNotValidException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // informaçoes ta errada
        defaultException.setMensagem(" Coloque Documento valido!! ");
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<DefaultException> handle(IncorrectResultSizeDataAccessException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem("Documento ja cadastrado!");
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DefaultException> handle(HttpMessageNotReadableException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem("Apenas PESSOA_FISICA, PESSOA_JURIDICA e GOVERNAMENTAL");
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }
}


//      @ExceptionHandler(ClienteCpfException.class)
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        DefaultException error = DefaultException.builder()
//                .title(status.getReasonPhrase())
//                .status(status.value())
//                .mensagem(e.getMessage()).build();
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error); // BAD RESQUEST
//
//    }

