package com.viii28stw.pensiltikfrontend.model.domain;

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
public class Sessao {
    private Usuario usuario;
    private boolean requerLogout;
}