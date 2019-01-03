package com.eighttwentyeightsoftware.pensiltikbackend.service;

import com.eighttwentyeightsoftware.pensiltikbackend.model.dto.UsuarioDto;
import com.eighttwentyeightsoftware.pensiltikbackend.model.entity.Usuario;
import com.eighttwentyeightsoftware.pensiltikbackend.repository.UsuarioRepository;
import com.eighttwentyeightsoftware.pensiltikbackend.util.ConvertorDtoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto buscarUsuarioPorId(String id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if(usuarioOptional.isPresent()){
            return ConvertorDtoEntity.convertUsuarioToUsuarioDto(usuarioOptional.get());
        } else throw new NoSuchElementException("Não existe usuário com o ID informado");
    }

    @Override
    public List<UsuarioDto> buscarTodosOsUsuarios(){
        List<UsuarioDto> usuariosDto = new ArrayList();
        for(Usuario usuario : usuarioRepository.findAll()) {
            usuariosDto.add(ConvertorDtoEntity.convertUsuarioToUsuarioDto(usuario));
        }
        return usuariosDto;
    }

    @Override
    public UsuarioDto salvarUsuario(UsuarioDto usuarioDto) {
        if(usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new IllegalArgumentException("Este e-mail já existe");
        }
        Usuario usuario = usuarioRepository.save(ConvertorDtoEntity.convertUsuarioDtoToUsuario(usuarioDto));
        return ConvertorDtoEntity.convertUsuarioToUsuarioDto(usuario);
    }

    @Override
    public UsuarioDto atualizarUsuario(UsuarioDto usuarioDto){
        if (usuarioDto.getId() == null || usuarioDto.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("O usuário informado não contem ID");
        }
        Usuario usuario = usuarioRepository.save(ConvertorDtoEntity.convertUsuarioDtoToUsuario(usuarioDto));
        return ConvertorDtoEntity.convertUsuarioToUsuarioDto(usuario);
    }

    @Override
    public void deletarUsuarioPorId(String id){
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioDto login(String email, String senha){
        Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);
        if(usuario == null) {
            throw new IllegalArgumentException("E-mail ou senha incorreta");
        }
        return ConvertorDtoEntity.convertUsuarioToUsuarioDto(usuarioRepository.findByEmailAndSenha(email, senha));
    }

}
