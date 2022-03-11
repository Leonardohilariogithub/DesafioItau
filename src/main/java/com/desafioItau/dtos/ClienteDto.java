package com.desafioItau.dtos;

import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.enums.EnumTipoPessoaCpfOuCnpj;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDto {

    private String nome;

    @Enumerated(EnumType.STRING)
    private EnumTipoPessoaCpfOuCnpj tipoDocumento;

    private String documento;

    private String telefone;

    private String endereco;

    private LocalDateTime registro;// regra que vou colocar


}
