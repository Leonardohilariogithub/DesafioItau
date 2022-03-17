package com.desafioItau.exceptions.handle;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Builder
@Getter
@Data
public class DefaultException {
    //private String title;
    private Integer status;
    private String mensagem;
    private String dataAtual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

}
