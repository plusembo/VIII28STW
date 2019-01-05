package com.eighttwentyeightsoftware.pensiltikbackend.service;

import com.eighttwentyeightsoftware.pensiltikbackend.enumeration.SexoEnum;
import com.eighttwentyeightsoftware.pensiltikbackend.model.dto.UsuarioDto;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import static com.eighttwentyeightsoftware.pensiltikbackend.util.RandomValue.randomAlphabetic;
import static com.eighttwentyeightsoftware.pensiltikbackend.util.RandomValue.randomAlphanumeric;
import static org.junit.Assert.*;
import org.springframework.test.context.junit4.SpringRunner;
import javax.persistence.RollbackException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Test(expected = IllegalArgumentException.class)
    public void salvarUsuarioNaoPodeRetornarNuloEnaoDeixarSalvarDoisUsuariosComEmailJaExistente() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());

        assertNotNull(usuarioDto);
        usuarioService.salvarUsuario(usuarioDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioSemNome() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioComNomeVazio() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome("   ")
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioSemSobrenome() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioComSobrenomeVazio() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome("    ")
                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioSemEmail() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioComEmailVazio() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email("    ")
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioComFormatoErrado() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7) + "." + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioSemSenha() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioComSenhaVazio() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .senha("     ")
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDeixarSalvarUsuarioSemSexo() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .senha(randomAlphanumeric(8))
                .dataNascimento(new Date())
                .build());

        usuarioService.salvarUsuario(usuarioDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buscarUsuarioPorIdNaoPodeRetornarNulo() {
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());

        assertNotNull(usuarioDto);

        UsuarioDto usuarioDto1 = usuarioService.buscarUsuarioPorId(usuarioDto.getId());

        assertNotNull(usuarioDto1);
        assertTrue(usuarioDto.equals(usuarioDto1));

        assertTrue(usuarioService.deletarUsuarioPorId(usuarioDto.getId()));
        usuarioService.buscarUsuarioPorId(usuarioDto.getId());
    }




    @Test
    public void buscarTodosOsUsuarios(){
        UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(new Date())
                .build());

        assertNotNull(usuarioDto);

        List<UsuarioDto> listUsuariosDto = usuarioService.buscarTodosOsUsuarios();

        assertNotNull(listUsuariosDto);
        assertTrue(listUsuariosDto.size() > 0);
        assertNotNull(listUsuariosDto.get(0));
    }


//
//    @Test
//    public void testUpdateUsuario() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        List<UsuarioDto> listUsuariosDto = usuarioService.findAllUsuarios();
//
//        assertNotNull(listUsuariosDto);
//        assertTrue(listUsuariosDto.size() > 0);
//        assertNotNull(listUsuariosDto.get(0));
//
//        UsuarioDto usuarioDto = listUsuariosDto.get(0);
//
//        usuarioDto.setNome(randomAlphabetic(25));
//        usuarioDto.setSobreNome(randomAlphabetic(25));
//        usuarioDto.setEmail(randomAlphabetic(15));
//        usuarioDto.setSenha(randomAlphanumeric(8));
//
//        assertTrue(usuarioService.updateUsuario(usuarioDto));
//        assertEquals(usuarioService.findUsuarioById(usuarioDto.getId()), usuarioDto);
//    }
//
//    @Test
//    public void testDeleteUsuarioById() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        List<UsuarioDto> listUsuariosDto = usuarioService.findAllUsuarios();
//
//        assertNotNull(listUsuariosDto);
//        assertTrue(listUsuariosDto.size() > 0);
//        assertNotNull(listUsuariosDto.get(0));
//
//        UsuarioDto usuarioDto = listUsuariosDto.get(0);
//        try {
//            assertTrue(usuarioService.deleteUsuarioById(usuarioDto.getId()));
//            assertNull(usuarioService.findUsuarioById(usuarioDto.getId()));
//        } catch (RollbackException rbe) {
//        }
//    }
//
//    @Test
//    public void testLogin() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        List<UsuarioDto> listUsuariosDto = usuarioService.findAllUsuarios();
//
//        assertNotNull(listUsuariosDto);
//        assertTrue(listUsuariosDto.size() > 0);
//        assertNotNull(listUsuariosDto.get(0));
//
//        UsuarioDto usuarioDto = listUsuariosDto.get(0);
//
//        assertNotNull(usuarioService.login(usuarioDto.getEmail(), usuarioDto.getSenha()));
//        assertEquals(usuarioService.login(usuarioDto.getEmail(), usuarioDto.getSenha()), usuarioDto);
//
//        try {
//            assertTrue(usuarioService.deleteUsuarioById(usuarioDto.getId()));
//            assertNull(usuarioService.findUsuarioById(usuarioDto.getId()));
//            assertNull(usuarioService.login(usuarioDto.getEmail(), usuarioDto.getSenha()));
//        } catch (RollbackException rbe) {
//        }
//    }
//
//    @Test
//    public void testIsUsuarioExist() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        List<UsuarioDto> listUsuariosDto = usuarioService.findAllUsuarios();
//
//        assertNotNull(listUsuariosDto);
//        assertTrue(listUsuariosDto.size() > 0);
//        assertNotNull(listUsuariosDto.get(0));
//
//        UsuarioDto usuarioDto = listUsuariosDto.get(0);
//
//        assertTrue(usuarioService.isUsuarioExists(usuarioDto));
//
//        try {
//            assertTrue(usuarioService.deleteUsuarioById(usuarioDto.getId()));
//            assertNull(usuarioService.findUsuarioById(usuarioDto.getId()));
//            assertFalse(usuarioService.isUsuarioExists(usuarioDto)); //O registro não deve mais existir após ser deletado
//        } catch (RollbackException rbe) {
//        }
//    }
//
//    @Test
//    public void testIsEmailUsuarioExists() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        List<UsuarioDto> listUsuariosDto = usuarioService.findAllUsuarios();
//
//        assertNotNull(listUsuariosDto);
//        assertTrue(listUsuariosDto.size() > 0);
//        assertNotNull(listUsuariosDto.get(0));
//
//        UsuarioDto usuarioDto = listUsuariosDto.get(0);
//
//        assertTrue(usuarioService.isEmailUsuarioExists(usuarioDto.getEmail()));
//
//        try {
//            assertTrue(usuarioService.deleteUsuarioById(usuarioDto.getId()));
//            assertNull(usuarioService.findUsuarioById(usuarioDto.getId()));
//            assertFalse(usuarioService.isEmailUsuarioExists(usuarioDto.getEmail())); //O registro não deve mais existir após ser deletado
//        } catch (RollbackException rbe) {
//        }
//    }

}
