package com.desafioItau.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "O nome não pode ficar em branco")
    private String nome;

    @Column(unique = true)
    @CPF
    private String cpf;

    @Column(unique = true)
    @CNPJ
    private String cnpj;

    @NotNull(message = "O nome não pode ser nulo")
    private String telefone;

    @NotBlank
    private String endereco;

}
