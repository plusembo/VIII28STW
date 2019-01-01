package com.eighttwentyeightsoftware.pensiltikbackend.util;

import com.eighttwentyeightsoftware.pensiltikbackend.model.dto.UsuarioDto;
import com.eighttwentyeightsoftware.pensiltikbackend.model.entity.Usuario;
import com.eighttwentyeightsoftware.pensiltikbackend.util.SimpleDateFormatFactory;

import java.text.ParseException;

public class ConvertorDtoEntity {

    public static Usuario convertUsuarioDtoToUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = null;
        try {
            usuario = Usuario.builder()
                    .id(usuarioDto.getId())
                    .nome(usuarioDto.getNome())
                    .sobreNome(usuarioDto.getSobreNome())
                    .email(usuarioDto.getEmail())
                    .senha(usuarioDto.getSenha())
                    .sexoEnum(usuarioDto.getSexoEnum())
                    .dataNascimento(SimpleDateFormatFactory.getSimpleDateFormat().parse(usuarioDto.getDataNascimento()))
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public static UsuarioDto convertUsuarioToUsuarioDto(Usuario usuario) {
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobreNome(usuario.getSobreNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .sexoEnum(usuario.getSexoEnum())
                .dataNascimento(SimpleDateFormatFactory.getSimpleDateFormat().format(usuario.getDataNascimento()))
                .build();

        return usuarioDto;
    }

}
