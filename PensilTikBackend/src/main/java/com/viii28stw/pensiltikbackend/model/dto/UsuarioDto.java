package com.viii28stw.pensiltikbackend.model.dto;

import com.viii28stw.pensiltikbackend.enumeration.SexoEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.viii28stw.pensiltikbackend.enumeration.UsuarioNivelAcessoEnum;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UsuarioDto{

    private String codigo;
    @NotBlank private String nome;
    @NotBlank private String sobreNome;
    @NotBlank @Email private String email;
    @NotBlank private String senha;
    private SexoEnum sexoEnum;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataNascimento;

    private UsuarioNivelAcessoEnum usuarioNivelAcessoEnum;

}
