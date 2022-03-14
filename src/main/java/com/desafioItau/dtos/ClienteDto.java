package com.desafioItau.dtos;

import com.desafioItau.enums.EnumTipoPessoaCpfOuCnpj;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDto {

    private Long id;

    @NotNull //obrigatorio
    @Column(nullable = false,unique = true,length = 35)
    private String nome;

    @Enumerated(EnumType.STRING)
    private EnumTipoPessoaCpfOuCnpj tipoDocumento;

    @NotNull
    private String documento;

    @NotNull
    private String telefone;

    @NotNull
    private String endereco;

    private LocalDateTime registro;// regra que vou colocar


}
