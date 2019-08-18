package com.viii28stw.pensiltikbackend.service;

import com.viii28stw.pensiltikbackend.enumeration.Sexo;
import com.viii28stw.pensiltikbackend.enumeration.UsuarioNivelAcesso;
import com.viii28stw.pensiltikbackend.model.dto.UsuarioDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static com.viii28stw.pensiltikbackend.util.RandomValue.randomAlphabetic;
import static com.viii28stw.pensiltikbackend.util.RandomValue.randomAlphanumeric;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UsuarioServiceTest {

    @Autowired
    private IUsuarioService usuarioService;

    @Test(expected = IllegalArgumentException.class)
    public void salvarUsuarioNaoPodeInformarEmailInvalido() {
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email("@".concat(randomAlphabetic(5)).concat(".").concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(10))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        usuarioService.salvarUsuario(usuarioDto);

        usuarioDto.setEmail(randomAlphabetic(7));
        usuarioService.salvarUsuario(usuarioDto);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@"));
        usuarioService.salvarUsuario(usuarioDto);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@").concat(randomAlphabetic(5)));
        usuarioService.salvarUsuario(usuarioDto);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@").concat(".").concat(randomAlphabetic(3)));
        usuarioService.salvarUsuario(usuarioDto);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@").concat(randomAlphabetic(5)).concat("."));
        usuarioService.salvarUsuario(usuarioDto);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@")
                .concat(randomAlphabetic(5)).concat(".")
                .concat(randomAlphabetic(1)));
        usuarioService.salvarUsuario(usuarioDto);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@")
                .concat(randomAlphabetic(5)).concat(".")
                .concat(randomAlphabetic(8)));
        usuarioService.salvarUsuario(usuarioDto);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void salvarUsuarioSenhaNaoPodeTerTamanhoMaiorQue10() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(11))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void salvarUsuarioNaoPodeRetornarNuloEnaoDeixarSalvarDoisUsuariosComEmailJaExistente() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(10))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());

        assertNotNull(usuarioDto);
        usuarioService.salvarUsuario(usuarioDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void atualizarUsuarioNaoPodeRetornarNuloEOUsuarioASerAtualizadoDeveConterID() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());

        assertNotNull(usuarioDto);

        usuarioDto.setNome(randomAlphabetic(25));
        usuarioDto.setSobreNome(randomAlphabetic(25));
        usuarioDto.setEmail(randomAlphabetic(7).concat("@")
                .concat(randomAlphabetic(5)).concat(".")
                .concat(randomAlphabetic(3)));
        usuarioDto.setSenha(randomAlphanumeric(8));
        usuarioDto.setUsuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM);
        usuarioDto.setSexo(Sexo.FEMININO);
        usuarioDto.setDataNascimento(LocalDate.now());

        assertNotNull(usuarioService.atualizarUsuario(usuarioDto));

        UsuarioDto usuarioDto1 = usuarioService.buscarUsuarioPorId(usuarioDto.getCodigo());
        assertNotNull(usuarioDto1);
        assertEquals(usuarioDto1, usuarioDto);

        usuarioDto.setCodigo(null);
        usuarioService.atualizarUsuario(usuarioDto);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void naoDeixarSalvarUsuarioSemNome() {
        usuarioService.salvarUsuario(UsuarioDto.builder()
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void naoDeixarSalvarUsuarioSemSobrenome() {
        usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioSemEmail() {
        usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void naoDeixarSalvarUsuarioSemSenha() {
        usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void naoDeixarSalvarUsuarioSemSexo() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .dataNascimento(LocalDate.now())
                .build());

        usuarioService.salvarUsuario(usuarioDto);
    }

    @Test
    public void buscarUsuarioMaiorCodigo() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());

        assertNotNull(usuarioDto);

        UsuarioDto usuarioDto1 = usuarioService.buscarUsuarioMaiorCodigo();

        assertNotNull(usuarioDto1);
        assertEquals(usuarioDto, usuarioDto1);
    }

    @Test(expected = NoSuchElementException.class)
    public void buscarUsuarioPorIdNaoPodeRetornarNulo() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());

        assertNotNull(usuarioDto);

        UsuarioDto usuarioDto1 = usuarioService.buscarUsuarioPorId(usuarioDto.getCodigo());

        assertNotNull(usuarioDto1);
        assertEquals(usuarioDto, usuarioDto1);

        assertTrue(usuarioService.deletarUsuarioPorId(usuarioDto.getCodigo()));
        usuarioService.buscarUsuarioPorId(usuarioDto.getCodigo());
    }

    @Test
    public void buscarTodosOsUsuarios() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());

        assertNotNull(usuarioDto);

        List<UsuarioDto> listUsuariosDto = usuarioService.buscarTodosOsUsuarios();

        assertNotNull(listUsuariosDto);
        assertFalse(listUsuariosDto.isEmpty());
        assertNotNull(listUsuariosDto.get(0));
    }

    @Test(expected = NoSuchElementException.class)
    public void deletarUsuarioPorId() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());

        assertNotNull(usuarioDto);

        assertTrue(usuarioService.deletarUsuarioPorId(usuarioDto.getCodigo()));
        usuarioService.buscarUsuarioPorId(usuarioDto.getCodigo());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deletarUsuarioPorIdInexistente() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());

        assertNotNull(usuarioDto);

        assertTrue(usuarioService.deletarUsuarioPorId(usuarioDto.getCodigo()));
        usuarioService.deletarUsuarioPorId(usuarioDto.getCodigo());
    }

    public void fazerLogin() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build());

        assertNotNull(usuarioDto);

        UsuarioDto usuarioDto1 = usuarioService.fazerLogin(usuarioDto);

        assertNotNull(usuarioDto1);
        assertEquals(usuarioDto1, usuarioDto);

        assertTrue(usuarioService.deletarUsuarioPorId(usuarioDto.getCodigo()));
        assertNull(usuarioService.fazerLogin(usuarioDto));
    }

}
