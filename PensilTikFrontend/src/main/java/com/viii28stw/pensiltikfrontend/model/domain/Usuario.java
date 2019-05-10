package com.viii28stw.pensiltikfrontend.model.domain;

import com.viii28stw.pensiltikfrontend.enumeration.SexoEnum;
import com.viii28stw.pensiltikfrontend.enumeration.UsuarioNivelAcessoEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nome;
    private String sobreNome;
    private String email;
    private String senha;
    private SexoEnum sexoEnum;
    private LocalDate dataNascimento;
    private UsuarioNivelAcessoEnum usuarioNivelAcessoEnum;
}
