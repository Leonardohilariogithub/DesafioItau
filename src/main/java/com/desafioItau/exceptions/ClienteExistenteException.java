package com.desafioItau.exceptions;

public class ClienteExistenteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ClienteExistenteException(String msg){
        super(msg);
    }
}
