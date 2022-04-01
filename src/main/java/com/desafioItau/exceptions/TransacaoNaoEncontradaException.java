package com.desafioItau.exceptions;

public class TransacaoNaoEncontradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TransacaoNaoEncontradaException(String msg) {
        super(msg);
    }
}
