package com.viii28stw.pensiltikfrontend.service;


import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import java.util.List;

/**
 * @author Plamedi L. Lusembo
 */

public interface UsuarioService {

    UsuarioDto buscarUsuarioPorId(String id);

    List<UsuarioDto> buscarTodosOsUsuarios();

    UsuarioDto salvarUsuario(UsuarioDto usuarioDto);

    UsuarioDto atualizarUsuario(UsuarioDto usuarioDto);

    boolean deletarUsuarioPorId(String id);

    UsuarioDto fazerLogin(String email, String senha);

}
