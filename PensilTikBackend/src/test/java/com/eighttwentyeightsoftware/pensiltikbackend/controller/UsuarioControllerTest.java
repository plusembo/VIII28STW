package com.eighttwentyeightsoftware.pensiltikbackend.controller;


import com.eighttwentyeightsoftware.pensiltikbackend.enumeration.SexoEnum;
import com.eighttwentyeightsoftware.pensiltikbackend.model.dto.UsuarioDto;
import com.eighttwentyeightsoftware.pensiltikbackend.util.SimpleDateFormatFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import com.eighttwentyeightsoftware.pensiltikbackend.util.UrlPrefixFactory;
import java.util.Date;
import static org.assertj.core.api.BDDAssertions.then;
import static com.cecilsoftwares.pensiltikbackend.util.RandomValue.randomAlphabetic;
import static com.cecilsoftwares.pensiltikbackend.util.RandomValue.randomAlphanumeric;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Test
    public void testSaveUsuario() {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
//                .email(randomAlphabetic(7) + "@" + randomAlphabetic(5) + "."+ randomAlphabetic(3))
                .email("herve@live.fr")
                .senha(randomAlphanumeric(8))
                .sexoEnum(SexoEnum.MASCULINO)
                .dataNascimento(SimpleDateFormatFactory.getSimpleDateFormat().format(new Date()))
                .build();

        ResponseEntity responseSaveUsuario = testRestTemplate
                .exchange(UrlPrefixFactory.getUrlPrefix() + "/salvarusuario", HttpMethod.POST, new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseSaveUsuario.getStatusCode()).isEqualTo(HttpStatus.CREATED);

//        ResponseEntity responseSaveUsuarioThatExixts = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/saveusuario", HttpMethod.POST, new HttpEntity<>(usuarioDto, httpHeaders), String.class);
//
//        then(responseSaveUsuarioThatExixts.getStatusCode()).isEqualTo(HttpStatus.CONFLICT); //Não pode deixar salvar dois usuários com o mesmo nome de usuário
    }
//
//    @Test
//    public void testFindAllUsuarios() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        ResponseEntity<UsuarioDto[]> responseFindAllUsuarios = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/findallusuarios", HttpMethod.GET, new HttpEntity<>(httpHeaders), UsuarioDto[].class);
//
//        then(responseFindAllUsuarios.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        assertNotNull(responseFindAllUsuarios.getBody());
//        then(responseFindAllUsuarios.getBody().length > 0);
//        assertNotNull(responseFindAllUsuarios.getBody()[0]);
//        then(responseFindAllUsuarios.getBody()[0] instanceof UsuarioDto);
//    }
//
//    @Test
//    public void testFindUsuarioById() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        HttpEntity request = new HttpEntity<>(httpHeaders);
//
//        ResponseEntity<UsuarioDto[]> responseFindAllUsuarios = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/findallusuarios", HttpMethod.GET, request, UsuarioDto[].class);
//
//        then(responseFindAllUsuarios.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        assertNotNull(responseFindAllUsuarios.getBody());
//        then(responseFindAllUsuarios.getBody().length > 0);
//        assertNotNull(responseFindAllUsuarios.getBody()[0]);
//        then(responseFindAllUsuarios.getBody()[0] instanceof UsuarioDto);
//
//        UsuarioDto usuarioDto = responseFindAllUsuarios.getBody()[0];
//
//        ResponseEntity<UsuarioDto> responseFindUsuarioById = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/findusuariobyid/" + usuarioDto.getId(), HttpMethod.GET, request, UsuarioDto.class);
//
//        then(responseFindUsuarioById.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        assertNotNull(responseFindUsuarioById.getBody());
//        then(responseFindUsuarioById.getBody() instanceof  UsuarioDto);
//    }
//
//    @Test
//    public void testUpdateUsuario() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        HttpEntity request = new HttpEntity<>(httpHeaders);
//
//        ResponseEntity<UsuarioDto[]> responseFindAllUsuarios= testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/findallusuarios", HttpMethod.GET, request, UsuarioDto[].class);
//
//        then(responseFindAllUsuarios.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        assertNotNull(responseFindAllUsuarios.getBody());
//        then(responseFindAllUsuarios.getBody().length > 0);
//        assertNotNull(responseFindAllUsuarios.getBody()[0]);
//        then(responseFindAllUsuarios.getBody()[0] instanceof UsuarioDto);
//
//        UsuarioDto usuarioDto = responseFindAllUsuarios.getBody()[0];
//
//        usuarioDto.setNome(randomAlphabetic(25));
//        usuarioDto.setSobreNome(randomAlphabetic(25));
//        usuarioDto.setSenha(randomAlphanumeric(8));
//        usuarioDto.setEmail(randomAlphabetic(15));
//
//        ResponseEntity responseUpDateUsuario = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/updateusuario", HttpMethod.PUT, new HttpEntity<>(usuarioDto, httpHeaders), String.class);
//
//        then(responseUpDateUsuario.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        ResponseEntity<UsuarioDto> responseFindUsuarioById = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/findusuariobyid/" + usuarioDto.getId(), HttpMethod.GET, request, UsuarioDto.class);
//
//        then(responseFindUsuarioById.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        assertNotNull(responseFindUsuarioById.getBody());
//        then(responseFindUsuarioById.getBody() instanceof  UsuarioDto);
//        assertEquals(responseFindUsuarioById.getBody(), usuarioDto);
//    }
//
//    @Test
//    public void testDeleteUsuarioById() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        HttpEntity request = new HttpEntity<>(httpHeaders);
//
//        ResponseEntity<UsuarioDto[]> responseFindAllUsuarios = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/findallusuarios", HttpMethod.GET, request, UsuarioDto[].class);
//
//        then(responseFindAllUsuarios.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        assertNotNull(responseFindAllUsuarios.getBody());
//        then(responseFindAllUsuarios.getBody().length > 0);
//        assertNotNull(responseFindAllUsuarios.getBody()[0]);
//        then(responseFindAllUsuarios.getBody()[0] instanceof UsuarioDto);
//
//        UsuarioDto usuarioDto = responseFindAllUsuarios.getBody()[0];
//
//        ResponseEntity responseDeleteUsuarioById = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/deleteusuariobyid/" + usuarioDto.getId(), HttpMethod.DELETE,  request, String.class);
//
//        then(responseDeleteUsuarioById.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        ResponseEntity<UsuarioDto> responseFindUsuarioById = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/findusuariobyid/" + usuarioDto.getId(), HttpMethod.GET, request, UsuarioDto.class);
//
//        then(responseFindUsuarioById.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//        assertNull(responseFindUsuarioById.getBody());
//    }
//
//    @Test
//    public void testLogin() {
//        testSaveUsuario(); //Para garantir que existirá pelo menos um registro no banco
//
//        HttpEntity request = new HttpEntity<>(httpHeaders);
//
//        ResponseEntity<UsuarioDto[]> responseFindAllUsuarios = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/findallusuarios", HttpMethod.GET, request, UsuarioDto[].class);
//
//        then(responseFindAllUsuarios.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        assertNotNull(responseFindAllUsuarios.getBody());
//        then(responseFindAllUsuarios.getBody().length > 0);
//        assertNotNull(responseFindAllUsuarios.getBody()[0]);
//        then(responseFindAllUsuarios.getBody()[0] instanceof UsuarioDto);
//
//        UsuarioDto usuarioDto = responseFindAllUsuarios.getBody()[0];
//
//        ResponseEntity<UsuarioDto> responseLogin = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/login/" + usuarioDto.getEmail() + "/" + usuarioDto.getSenha(), HttpMethod.GET, request, UsuarioDto.class);
//
//        then(responseLogin.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        assertNotNull(responseLogin.getBody());
//        then(responseLogin.getBody() instanceof UsuarioDto);
//        assertEquals(responseLogin.getBody(), usuarioDto);
//
//        ResponseEntity responseDeleteUsuarioById = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/deleteusuariobyid/" + usuarioDto.getId(), HttpMethod.DELETE,  request, String.class);
//
//        then(responseDeleteUsuarioById.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        ResponseEntity<UsuarioDto> responseFindUsuarioById = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/findusuariobyid/" + usuarioDto.getId(), HttpMethod.GET, request, UsuarioDto.class);
//
//        then(responseFindUsuarioById.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//        assertNull(responseFindUsuarioById.getBody());
//
//        ResponseEntity<UsuarioDto> responseLogin2 = testRestTemplate
//                .exchange(UrlPrefixFactory.getUrlPrefix() + "/login/" + usuarioDto.getEmail() + "/" + usuarioDto.getSenha(), HttpMethod.GET, request, UsuarioDto.class);
//
//        then(responseLogin2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//        assertNull(responseLogin2.getBody());
//    }

}
