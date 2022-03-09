package com.desafioItau.dtos;

import com.desafioItau.entidades.ClienteEntidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    @NotBlank  // Anotaçoes da dependecia Validation
    private String nome;
    @NotBlank 
    private String cpf;
    @NotBlank
    private String telefone;
    @NotBlank
    private String endereco;

    public ClienteEntidade transformaParaObjeto() { //POST
        return new ClienteEntidade(nome, cpf, telefone, endereco);
    }

}
