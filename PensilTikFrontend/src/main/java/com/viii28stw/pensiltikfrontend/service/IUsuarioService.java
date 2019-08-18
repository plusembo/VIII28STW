package com.viii28stw.pensiltikfrontend.service;


import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;

import java.util.List;

public interface IUsuarioService {

    UsuarioDto buscarUsuarioMaiorCodigo();

    UsuarioDto buscarUsuarioPorId(String id);

    List<UsuarioDto> buscarTodosOsUsuarios();

    UsuarioDto salvarUsuario(UsuarioDto usuarioDto);

    UsuarioDto atualizarUsuario(UsuarioDto usuarioDto);

    boolean deletarUsuarioPorId(String id);

    UsuarioDto login(String email, String password);

    void logout(String email);

}
