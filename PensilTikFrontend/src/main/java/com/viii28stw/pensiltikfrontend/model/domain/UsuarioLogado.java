package com.eighttwentyeightsoftware.pensiltikfrontend.model.domain;

import com.viii28stw.pensiltikfrontend.model.entity.Usuario;
import lombok.*;
import org.springframework.stereotype.Component;

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
@Component
public class UsuarioLogado {
    private Usuario usuario;
    private boolean requerLogout;
}