package com.desafioItau.dtos;

import com.desafioItau.entidades.ClienteEntidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    private String nome;

    private String cpf;

    private String telefone;

    private String endereco;

}
