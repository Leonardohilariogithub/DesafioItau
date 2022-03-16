package com.desafioItau.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDto {

    private Long id;

    @NotBlank //obrigatorio
    private String nome;

    @CPF
    private String cpf;

    @CNPJ
    private String cnpj;

    @NotBlank
    private String telefone;

    @NotBlank
    private String endereco;

    private LocalDateTime registro;// regra que vou colocar


}
