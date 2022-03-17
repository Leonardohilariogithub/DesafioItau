package com.desafioItau.exceptions;

public class IncorrectResultSizeDataAccessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IncorrectResultSizeDataAccessException(String msg) {
        super(msg);
    }
}
