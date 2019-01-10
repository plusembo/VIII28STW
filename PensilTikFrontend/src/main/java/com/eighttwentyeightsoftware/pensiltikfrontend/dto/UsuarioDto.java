package com.eighttwentyeightsoftware.pensiltikfrontend.dto;

import com.eighttwentyeightsoftware.pensiltikbackend.enumeration.SexoEnum;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UsuarioDto{

    private String id;

    private String nome;

    private String sobreNome;

    private String email;

    private String senha;

    private SexoEnum sexoEnum;

    private Date dataNascimento;
}
