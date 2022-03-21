package com.desafioItau.exceptions;

public class ContaNaoEncontradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ContaNaoEncontradaException(String msg) {
        super(msg);
    }
}
