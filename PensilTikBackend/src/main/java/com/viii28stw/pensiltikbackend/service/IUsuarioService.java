package com.viii28stw.pensiltikbackend.service;


import com.viii28stw.pensiltikbackend.model.dto.UsuarioDto;
import java.util.List;

public interface IUsuarioService {

    UsuarioDto buscarUsuarioMaiorCodigo();

    boolean isUserLoggedIn(String email);

    UsuarioDto buscarUsuarioPorId(String id);

    List<UsuarioDto> buscarTodosOsUsuarios();

    UsuarioDto salvarUsuario(UsuarioDto usuarioDto);

    UsuarioDto atualizarUsuario(UsuarioDto usuarioDto);

    boolean deletarUsuarioPorId(String id);

    UsuarioDto fazerLogin(UsuarioDto usuarioDto);

    void sair(String email);

}
