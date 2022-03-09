package com.desafioItau.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperacaoDto {

    private String numeroDaConta;
    private String numeroDaContaDestino;

    //@Enumerated(EnumType.STRING)
    //private EnumOperacao tipoDaOperacao;

    private BigDecimal valorDaTransação;
    private BigDecimal taxa;
    private BigDecimal saldo;


}
