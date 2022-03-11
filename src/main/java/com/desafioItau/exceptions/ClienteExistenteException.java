package com.desafioItau.exceptions;

public class ClienteExistenteException extends RuntimeException {
    public ClienteExistenteException(String msg){
        super(msg);
    }

}
