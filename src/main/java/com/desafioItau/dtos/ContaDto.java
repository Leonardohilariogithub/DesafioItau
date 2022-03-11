package com.desafioItau.dtos;

import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.enums.EnumConta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContaDto {

    private ClienteEntidade clienteEntidade;


    private String agencia;

    private String numeroDaConta;

    @Enumerated(EnumType.STRING)
    private EnumConta tipoDaConta;

    private String digitoVerificador;

    private String clienteCpf;

}
