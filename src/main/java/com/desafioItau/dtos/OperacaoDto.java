package com.desafioItau.dtos;

import com.desafioItau.enums.EnumOperacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperacaoDto {

    private Long id;

    private String numeroDaConta;
    private String numeroDaContaDestino;

    @Enumerated(EnumType.STRING)
    private EnumOperacao tipoDaOperacao;        //TRANSFERENCIA(1),SAQUE(2), DEPOSITO(3);

    private BigDecimal valorDaTransação;
    private BigDecimal taxa;
    private BigDecimal saldo = BigDecimal.valueOf(0);

}
