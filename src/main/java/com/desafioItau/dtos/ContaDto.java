package com.desafioItau.dtos;

import com.desafioItau.enums.EnumConta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContaDto {

    private Long id;
    private String agencia;

    private String numeroDaConta;

    @Enumerated(EnumType.STRING)
    private EnumConta tipoDaConta;

    private String digitoVerificador;

    private String clienteCpf;

}
