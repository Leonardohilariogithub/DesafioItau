package com.desafioItau.exceptions;

import org.springframework.http.HttpInputMessage;

public class HttpMessageNotReadableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public HttpMessageNotReadableException(String msg, HttpInputMessage httpInputMessage) {
        super("Erro " + msg);
    }
}
