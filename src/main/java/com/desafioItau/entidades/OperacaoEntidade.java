package com.desafioItau.entidades;

import com.desafioItau.enums.EnumOperacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_operacao")
@JsonInclude(JsonInclude.Include.NON_NULL) // nulo nao vai retornar
public class OperacaoEntidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String numeroDaConta;

    private String numeroDaContaDestino;

    @Enumerated(EnumType.STRING)
    private EnumOperacao tipoDaOperacao;     //TRANSFERENCIA(1),SAQUE(2), DEPOSITO(3);

    @Column(nullable = false)
    private BigDecimal valorDaTransação;

    private BigDecimal taxa;

    private BigDecimal saldo;

    private String mensagem;

    private String aviso;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR",
            timezone = "America/São_Paulo")
    private LocalDateTime dataHora = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private ContaEntidade conta;
}