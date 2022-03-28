package com.desafioItau.entidades;

import com.desafioItau.enums.EnumTipoDaConta;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tb_conta")
public class ContaEntidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String agencia;

    @Column(nullable = false)
    private String numeroDaConta;

    @Enumerated(EnumType.STRING)
    private EnumTipoDaConta tipoDaConta;

    @Column(nullable = false)
    private String digitoVerificador;

    private String clienteCpf;

    private String clienteCnpj;

    private BigDecimal saldo = BigDecimal.valueOf(0);

    private int saqueSemTaxa;

    private String aviso;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR",
            timezone = "America/São_Paulo")
    private LocalDateTime dataHora = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntidade cliente;

}
