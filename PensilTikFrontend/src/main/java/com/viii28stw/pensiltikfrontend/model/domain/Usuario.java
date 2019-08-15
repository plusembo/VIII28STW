package com.viii28stw.pensiltikfrontend.model.domain;

import com.viii28stw.pensiltikfrontend.enumeration.Sexo;
import com.viii28stw.pensiltikfrontend.enumeration.UsuarioNivelAcesso;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@Service("usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codigo;
    private String nome;
    private String sobreNome;
    private String email;
    private String senha;
    private Sexo sexo;
    private LocalDate dataNascimento;
    private UsuarioNivelAcesso usuarioNivelAcesso;
}
