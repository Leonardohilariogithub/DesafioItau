package com.desafioItau.exceptions;

public class OperacoesNaoValidaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OperacoesNaoValidaException(String msg) {
        super(msg);
    }
}
