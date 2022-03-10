package com.desafioItau.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_conta")
public class ContaEntidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tb_cliente_id")
    private ClienteEntidade clienteEntidade;

    @Column(nullable = false)
    private String agencia;
    @Column(nullable = false)
    private String numeroDaConta;
    @Column(nullable = false)
    private String tipoDaConta;
    @Column(nullable = false)
    private String digitoVerificador;
    @Column(nullable = false)
    private String clienteCpf;
    @Column(nullable = false)
    private LocalDateTime registro;// regra que vou colocar

    public ContaEntidade(String agencia, String numeroDaConta, String tipoDaConta, String digitoVerificador,String clienteCpf) {   //POST
        this.id = id;
        this.agencia = agencia;
        this.numeroDaConta = numeroDaConta;
        this.tipoDaConta = tipoDaConta;
        this.digitoVerificador = digitoVerificador;
        this.clienteCpf = clienteCpf;
    }
}
