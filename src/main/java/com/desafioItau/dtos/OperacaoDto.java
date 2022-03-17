package com.desafioItau.dtos;

import com.desafioItau.enums.EnumOperacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperacaoDto {

    private Long id;

    private String numeroDaConta;
    private String numeroDaContaDestino;

    @Enumerated(EnumType.STRING)
    private EnumOperacao tipoDaOperacao;

    private BigDecimal valorDaTransação;
    private BigDecimal taxa;

}
