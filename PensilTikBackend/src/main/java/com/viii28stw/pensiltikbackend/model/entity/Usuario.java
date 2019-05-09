package com.viii28stw.pensiltikbackend.model.entity;

import com.viii28stw.pensiltikbackend.enumeration.SexoEnum;
import com.viii28stw.pensiltikbackend.enumeration.UsuarioNivelAcessoEnum;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@Entity
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(name = "IdGenerator", strategy = "com.viii28stw.pensiltikbackend.util.IdGenerator")
    @GeneratedValue(generator = "IdGenerator")
    @Id
    @Column(name = "ID", length = 25)
    private String id;

    @Column(name = "NOME", length = 25, nullable = false)
    private String nome;

    @Column(name = "SOBRE_NOME", length = 25, nullable = false)
    private String sobreNome;

    @Email
    @Column(name = "EMAIL", length = 25, nullable = false)
    private String email;

    @Column(name = "SENHA", length = 10, nullable = false)
    private String senha;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "SEXO", nullable = false)
    private SexoEnum sexoEnum;

    @Column(name = "DATA_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "NIVEL_ACESSO", nullable = false)
    private UsuarioNivelAcessoEnum usuarioNivelAcessoEnum;

}
