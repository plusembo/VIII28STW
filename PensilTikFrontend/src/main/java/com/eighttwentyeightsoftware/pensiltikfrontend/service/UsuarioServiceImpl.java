package com.eighttwentyeightsoftware.pensiltikfrontend.service;

import com.eighttwentyeightsoftware.pensiltikfrontend.model.dto.UsuarioDto;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Plamedi L. Lusembo
 */

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

    @Override
    public UsuarioDto buscarUsuarioPorId(String id) {
    return null;
    }

    @Override
    public List<UsuarioDto> buscarTodosOsUsuarios(){
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
    public boolean deletarUsuarioPorId(String id){
        return false;
    }

    @Override
    public UsuarioDto fazerLogin(String email, String senha){
        return null;
    }

}
