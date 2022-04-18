package com.desafioItau.exceptions.handle;

import com.desafioItau.exceptions.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NonUniqueResultException;

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
        defaultException.setStatus(HttpStatus.NOT_FOUND.value()); // nao ser aceito
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultException> handle(MethodArgumentNotValidException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // informaçoes ta errada
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<DefaultException> handle(IncorrectResultSizeDataAccessException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<DefaultException> handle(NonUniqueResultException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<DefaultException> handle(ContaNaoEncontradaException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DefaultException> handle(HttpMessageNotReadableException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem("Cliente nao EXISTE!, Coloque tipo de documento Valido!");
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DefaultException> handle(ConstraintViolationException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem("Esse campo deve ser preenchido!");
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);

    }
    @ExceptionHandler(OperacoesException.class)
    public ResponseEntity<DefaultException> handle(OperacoesException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }
    @ExceptionHandler(OperacoesNaoValidaException.class)
    public ResponseEntity<DefaultException> handle(OperacoesNaoValidaException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.NOT_ACCEPTABLE.value()); // nao ser aceito
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<DefaultException> handle(SaldoInsuficienteException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }
    @ExceptionHandler(TransacaoException.class)
    public ResponseEntity<DefaultException> handle(TransacaoException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.BAD_REQUEST.value()); // nao ser aceito
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }
    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    public ResponseEntity<DefaultException> handle(TransacaoNaoEncontradaException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.NOT_FOUND.value()); // nao ser aceito
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }

    @ExceptionHandler(ServicoIndisponivelException.class)
    public ResponseEntity<DefaultException> handle(ServicoIndisponivelException e) {
        DefaultException defaultException = new DefaultException();
        defaultException.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value()); // nao ser aceito
        defaultException.setMensagem(e.getMessage());
        return ResponseEntity.status(defaultException.getStatus()).body(defaultException);
    }
}


