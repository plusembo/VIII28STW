package com.viii28stw.pensiltikfrontend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Plamedi L. Lusembo
 */

@AllArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
public enum SexoEnum {
    MASCULINO('M', "Masculino"),
    FEMININO('F', "Feminino");

    private final char id;
    @ToString.Include
    private final String descricao;
}
