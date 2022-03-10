package com.desafioItau.dtos;

import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.entidades.ContaEntidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDto {

    private ClienteEntidade clienteEntidade;

    @NotBlank
    private String agencia;
    @NotBlank
    private String numeroDaConta;
    @NotBlank
    private String tipoDaConta;
    @NotBlank
    private String digitoVerificador;
    @NotBlank
    private String clienteCpf;

}
