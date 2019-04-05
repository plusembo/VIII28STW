package com.viii28stw.pensiltikfrontend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Plamedi L. Lusembo
 */

@AllArgsConstructor
@Getter
public enum MenuEnum {
    //0.Configurações
    CONFIGURACOES_CONTA_USUARIO("CFG001", "MINHA CONTA"),
    //1.Cadastros
    CADASTRO_TIPO_RENDA("CAD001", "CADASTRO TIPO RENDA"),
    CADASTRO_RENDA("CAD002", "CADASTRO RENDA"),
    CADASTRO_TIPO_DESPESA("CAD003", "CADASTRO TIPO DESPESA"),
    CADASTRO_DESPESA("CAD004", "CADASTRO DESPESA"),
    //3.Relatórios
    RELATORIO_RENDA("REL001", "RELATÓRIO RENDA"),
    RELATORIO_DESPESAS("REL002", "RELATÓRIO DESPESAS"),
    //3.Sobre
    AJUDA_SOBRE("SBR001", "SOBRE");

    private final String id;
    private final String titulo;
}