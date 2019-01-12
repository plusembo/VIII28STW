package com.eighttwentyeightsoftware.pensiltikfrontend.model.entity;

import com.eighttwentyeightsoftware.pensiltikfrontend.enumeration.SexoEnum;
import lombok.*;
import org.joda.time.DateTime;
import java.io.Serializable;

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
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String nome;
    private String sobreNome;
    private String email;
    private String senha;
    private SexoEnum sexoEnum;
    private DateTime dataNascimento;

}
