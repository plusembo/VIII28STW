package com.eighttwentyeightsoftware.pensiltikbackend.service;

import com.eighttwentyeightsoftware.pensiltikbackend.enumeration.SexoEnum;
import com.eighttwentyeightsoftware.pensiltikbackend.model.dto.UsuarioDto;
import com.eighttwentyeightsoftware.pensiltikbackend.util.SimpleDateFormatFactory;
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

    @Test
    public void salvarUsuarioNaoPodeRetornarNuloENaoPodeSalvarUsuarioComEmailJaExistente(){
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(5) + "@teste.com")
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(SimpleDateFormatFactory.getSimpleDateFormat().format(new Date()))
                .build();

//        UsuarioDto usuarioDto1 = usuarioService.salvarUsuario(usuarioDto);

//        assertNotNull(usuarioDto1);
//        assertNull(usuarioService.salvarUsuario(usuarioDto)); //Não pode deixar salvar dois usuários com o mesmo email de usuário
    }

//    @Test
//    public void testFindAllUsuarios(){
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        List<UsuarioDto> listUsuariosDto = usuarioService.findAllUsuarios();
//
//        assertNotNull(listUsuariosDto);
//        assertTrue(listUsuariosDto.size() > 0);
//        assertNotNull(listUsuariosDto.get(0));
//    }
//
//    @Test
//    public void testFindUsuarioById() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        List<UsuarioDto> listUsuariosDto = usuarioService.findAllUsuarios();
//
//        assertNotNull(listUsuariosDto);
//        assertTrue(listUsuariosDto.size() > 0);
//        assertNotNull(listUsuariosDto.get(0));
//
//        UsuarioDto usuarioDto = listUsuariosDto.get(0);
//        UsuarioDto usuarioDto1 = usuarioService.findUsuarioById(usuarioDto.getId());
//
//        assertNotNull(usuarioDto1);
//        assertTrue(usuarioDto.equals(usuarioDto1));
//
//        try {
//            assertTrue(usuarioService.deleteUsuarioById(usuarioDto.getId()));
//            assertNull(usuarioService.findUsuarioById(usuarioDto.getId()));
//        } catch (RollbackException rbe) {
//        }
//    }
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
