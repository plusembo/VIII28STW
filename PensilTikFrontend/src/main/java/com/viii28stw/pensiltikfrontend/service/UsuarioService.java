package com.viii28stw.pensiltikfrontend.service;

import com.viii28stw.pensiltikfrontend.model.domain.Sessao;
import com.viii28stw.pensiltikfrontend.model.domain.Usuario;
import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
public class UsuarioService implements IUsuarioService {

    private static UsuarioService uniqueInstance;

    public static synchronized UsuarioService getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new UsuarioService();
        }
        return uniqueInstance;
    }

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
        return usuarioDto;
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
        UsuarioDto usuarioDto = null;
        if (usuarioDto != null) {
            Usuario usuario = Usuario.builder().id(usuarioDto.getId())
                    .nome(usuarioDto.getNome())
                    .sobreNome(usuarioDto.getSobreNome())
                    .email(usuarioDto.getEmail())
                    .senha(usuarioDto.getSenha())
                    .sexoEnum(usuarioDto.getSexoEnum())
                    .dataNascimento(usuarioDto.getDataNascimento())
                    .build();

            Sessao.getInstance().setUsuario(usuario);
            Sessao.getInstance().setRequerLogout(false);
        }
        return usuarioDto;
    }

}
