package com.desafioItau.entidades;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.enums.EnumConta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
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

    private String agencia;

    private String numeroDaConta;

    @Enumerated(EnumType.STRING)
    private EnumConta tipoDaConta;

    private String digitoVerificador;

    private String clienteCpf;


    private LocalDateTime registro;// regra que vou colocar

}
