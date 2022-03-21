package com.desafioItau.exceptions;

public class OperacoesException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OperacoesException(String msg) {
        super(msg);
    }
}
