package com.viii28stw.pensiltikfrontend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Plamedi L. Lusembo
 */

@AllArgsConstructor
@Getter
public enum Sexo {
    MASCULINO('M', "Masculino"),
    FEMININO('F', "Feminino");

    private final char id;
    private final String descricao;

    public static List<Sexo> getList() {
        List<Sexo> listSexo = new ArrayList<Sexo>
                (Arrays.asList(Sexo.values()));
        return listSexo;
    }

    @Override
    public String toString() {
        return this.descricao;
    }



}
