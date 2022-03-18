package com.desafioItau.exceptions;

public class ClienteCpfException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ClienteCpfException(String msg) {
        super(msg);
    }
}
