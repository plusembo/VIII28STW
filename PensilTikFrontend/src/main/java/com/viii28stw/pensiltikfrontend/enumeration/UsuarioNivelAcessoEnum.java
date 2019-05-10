package com.viii28stw.pensiltikfrontend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Plamedi L. Lusembo
 */

@AllArgsConstructor
@Getter
public enum UsuarioNivelAcessoEnum {
    MASCULINO(1,"Admin", "Administrador"),
    FEMININO(2,"Uscm", "Usuário comum");

    private final int id;
    private final String abreviacao;
    private final String descricao;
}
