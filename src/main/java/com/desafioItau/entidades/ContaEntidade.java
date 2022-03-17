package com.desafioItau.entidades;

import com.desafioItau.enums.EnumTipoDaConta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String agencia;

    private String numeroDaConta;

    @Enumerated(EnumType.STRING)
    private EnumTipoDaConta tipoDaConta;

    private String digitoVerificador;

    private String clienteCpf;

    private String clienteCnpj;

    private LocalDateTime registro;// regra que vou colocar

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntidade cliente;

    @JsonIgnore
    @OneToMany(mappedBy = "conta")
    private List<OperacaoEntidade> operacoes = new ArrayList<>();
}
