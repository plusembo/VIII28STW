package com.viii28stw.pensiltikfrontend.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
@ToString
public class Sessao {
    @Getter @Setter private Usuario usuario;
    @Getter @Setter private boolean logoutRequest;
    private static Sessao uniqueInstance;

    public static synchronized Sessao getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Sessao();
        }
        return uniqueInstance;
    }
}