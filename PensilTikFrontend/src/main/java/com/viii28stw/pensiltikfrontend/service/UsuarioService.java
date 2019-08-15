package com.viii28stw.pensiltikfrontend.service;

import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service("usuarioService")
public class UsuarioService implements IUsuarioService {

    private final Set<String> userLoggedIn = new HashSet();

    public boolean isUserLoggedIn(String email) {
        return userLoggedIn.stream()
                .filter(us -> us.equals(email)).findFirst().orElse(null) != null;
    }

    public UsuarioDto buscarUsuarioMaiorCodigo() {
        return null;
    }

    @Override
    public UsuarioDto buscarUsuarioPorId(String id) {
        return null;
    }

    @Override
    public List<UsuarioDto> buscarTodosOsUsuarios() {
        return null;
    }

    @Override
    public UsuarioDto salvarUsuario(UsuarioDto usuarioDto) {
        return null;
    }

    @Override
    public UsuarioDto atualizarUsuario(UsuarioDto usuarioDto) {
        return null;
    }

    private UsuarioDto persistir(UsuarioDto usuarioDto) {
        return null;
    }

    @Override
    public boolean deletarUsuarioPorId(String id) {
        return true;
    }

    @Override
    public UsuarioDto login(String email, String password) {
        return null;
    }

    @Override
    public void sair(String email) {
        if (userLoggedIn.remove(email)) {
            throw new NoSuchElementException("usuário não está logado");
        }
    }

}
