package com.desafioItau.exceptions;

public class ServicoIndisponivelException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ServicoIndisponivelException(String s) {
        super(s);
    }
}
