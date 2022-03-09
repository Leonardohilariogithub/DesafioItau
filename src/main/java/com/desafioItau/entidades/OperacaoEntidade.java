package com.desafioItau.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
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
public class OperacaoEntidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroDaConta;
    private String numeroDaContaDestino;

//    @Enumerated(EnumType.STRING)
//    private EnumOperacao tipoDaOperacao;        //TRANSFERENCIA(1),SAQUE(2), DEPOSITO(3);

    private BigDecimal valorDaTransação;
    private BigDecimal taxa;
    private BigDecimal saldo;

    @Column(nullable = false)
    private LocalDateTime registro;// regra que vou colocar     EnumOperacao tipoDaOperacao,


}