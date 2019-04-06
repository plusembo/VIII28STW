package com.viii28stw.pensiltikfrontend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Plamedi L. Lusembo
 */

@AllArgsConstructor
@Getter
public enum SexoEnum {
    MASCULINO('M', "Masculino"),
    FEMININO('F', "Feminino");

    private final char id;
    private final String descricao;

    public static List<SexoEnum> getList() {
        List<SexoEnum> listSexoEnum = new ArrayList<SexoEnum>
                (Arrays.asList(SexoEnum.values()));
        return listSexoEnum;
    }

    @Override
    public String toString() {
        return this.descricao;
    }



}
