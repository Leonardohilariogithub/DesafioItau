package com.desafioItau.dtos;

import com.desafioItau.enums.EnumOperacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperacaoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String numeroDaConta;

    private String numeroDaContaDestino;

    @Enumerated(EnumType.STRING)
    private EnumOperacao tipoDaOperacao;        //TRANSFERENCIA(1),SAQUE(2), DEPOSITO(3);

    private BigDecimal valorDaTransação;

    private BigDecimal taxa;

    private BigDecimal saldo;

    private String mensagem;

    private String aviso;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR",
            timezone = "America/São_Paulo")
    private LocalDateTime dataHora = LocalDateTime.now();

}
