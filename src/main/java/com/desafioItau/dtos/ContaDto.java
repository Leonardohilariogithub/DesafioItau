package com.desafioItau.dtos;

import com.desafioItau.enums.EnumTipoDaConta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ContaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String agencia;

    private String numeroDaConta;

    @Enumerated(EnumType.STRING)
    private EnumTipoDaConta tipoDaConta;      // PESSOA_FISICA, PESSOA_JURIDICA, GOVERNAMENTAL

    private int digitoVerificador;

    private String clienteCpf;

    private String clienteCnpj;

    private BigDecimal saldo = BigDecimal.valueOf(0);

    private int saqueSemTaxa;

    private String aviso;
}
