package com.viii28stw.pensiltikbackend.service;

import com.viii28stw.pensiltikbackend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikbackend.model.entity.Usuario;
import com.viii28stw.pensiltikbackend.repository.UsuarioRepository;
import com.viii28stw.pensiltikbackend.util.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("usuarioService")
public class UsuarioService implements IUsuarioService {

    @Autowired private UsuarioRepository usuarioRepository;
    private final Set<String> userLoggedIn = new HashSet();

    public boolean isUserLoggedIn(String email) {
        return userLoggedIn.stream()
                .filter(us -> us.equals(email)).findFirst().orElse(null) != null;
    }

    @Override
    public UsuarioDto buscarUsuarioPorId(String id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            return UsuarioDto.builder()
                    .id(usuario.getId())
                    .nome(usuario.getNome())
                    .sobreNome(usuario.getSobreNome())
                    .email(usuario.getEmail())
                    .usuarioNivelAcessoEnum(usuario.getUsuarioNivelAcessoEnum())
                    .senha(usuario.getSenha())
                    .sexoEnum(usuario.getSexoEnum())
                    .dataNascimento(usuario.getDataNascimento())
                    .build();

        } else throw new NoSuchElementException("Não existe usuário com o ID informado");
    }

    @Override
    public List<UsuarioDto> buscarTodosOsUsuarios(){
        List<UsuarioDto> usuariosDto = new ArrayList();
        for(Usuario usuario : usuarioRepository.findAll()) {
            usuariosDto.add(UsuarioDto.builder()
                    .id(usuario.getId())
                    .nome(usuario.getNome())
                    .sobreNome(usuario.getSobreNome())
                    .email(usuario.getEmail())
                    .usuarioNivelAcessoEnum(usuario.getUsuarioNivelAcessoEnum())
                    .senha(usuario.getSenha())
                    .sexoEnum(usuario.getSexoEnum())
                    .dataNascimento(usuario.getDataNascimento())
                    .build());
        }
        return usuariosDto;
    }

    @Override
    public UsuarioDto salvarUsuario(UsuarioDto usuarioDto) {
        if (!EmailValidator.isValidEmail(usuarioDto.getEmail())) {
                throw new IllegalArgumentException("Este e-mail não é válido");
        }else if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
                throw new IllegalArgumentException("Este e-mail já existe");
        }

        return persistir(usuarioDto);
    }

    @Override
    public UsuarioDto atualizarUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto.getId() == null || usuarioDto.getId().trim().isEmpty()) {
                throw new IllegalArgumentException("O usuário informado não contem ID");
        }

        return persistir(usuarioDto);
    }

    private UsuarioDto persistir(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.save(Usuario.builder()
                .id(usuarioDto.getId())
                .nome(usuarioDto.getNome())
                .sobreNome(usuarioDto.getSobreNome())
                .email(usuarioDto.getEmail())
                .usuarioNivelAcessoEnum(usuarioDto.getUsuarioNivelAcessoEnum())
                .senha(usuarioDto.getSenha())
                .sexoEnum(usuarioDto.getSexoEnum())
                .dataNascimento(usuarioDto.getDataNascimento())
                .build());

        return UsuarioDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobreNome(usuario.getSobreNome())
                .email(usuario.getEmail())
                .usuarioNivelAcessoEnum(usuario.getUsuarioNivelAcessoEnum())
                .senha(usuario.getSenha())
                .sexoEnum(usuario.getSexoEnum())
                .dataNascimento(usuario.getDataNascimento())
                .build();
    }

    @Override
    public boolean deletarUsuarioPorId(String id){
        usuarioRepository.deleteById(id);
        return true;
    }

    @Override
    public UsuarioDto fazerLogin(String email, String senha){
        if(usuarioRepository.findByEmailAndSenha(email, senha) == null) {
            throw new NoSuchElementException("E-mail ou senha incorreta");
        }
        Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);

        userLoggedIn.add(usuario.getEmail());

        return UsuarioDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobreNome(usuario.getSobreNome())
                .email(usuario.getEmail())
                .usuarioNivelAcessoEnum(usuario.getUsuarioNivelAcessoEnum())
                .senha(usuario.getSenha())
                .sexoEnum(usuario.getSexoEnum())
                .dataNascimento(usuario.getDataNascimento())
                .build();
    }

    @Override
    public void sair(String email){
        if(userLoggedIn.remove(email)) {
            throw new NoSuchElementException("usuário não está logado");
        }
    }

}
