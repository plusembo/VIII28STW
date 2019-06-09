package com.viii28stw.pensiltikfrontend.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.viii28stw.pensiltikfrontend.enumeration.SexoEnum;
import com.viii28stw.pensiltikfrontend.enumeration.UsuarioNivelAcessoEnum;
import lombok.*;

import java.time.LocalDate;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UsuarioDto{
    private String codigo;
    private String nome;
    private String sobreNome;
    private String email;
    private String senha;
    private SexoEnum sexoEnum;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataNascimento;

    private UsuarioNivelAcessoEnum usuarioNivelAcessoEnum;

}
