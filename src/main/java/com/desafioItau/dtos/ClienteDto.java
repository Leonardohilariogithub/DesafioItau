package com.desafioItau.dtos;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ClienteDto {

    @NotBlank
    private String nome;

    @NotBlank @Size(max = 11)
    private String cpf;

    @NotBlank
    private double telefone;

    @NotBlank
    private String endereço;
}
