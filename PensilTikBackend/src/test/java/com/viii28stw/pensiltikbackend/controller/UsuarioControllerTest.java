package com.viii28stw.pensiltikbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viii28stw.pensiltikbackend.enumeration.Sexo;
import com.viii28stw.pensiltikbackend.enumeration.UsuarioNivelAcesso;
import com.viii28stw.pensiltikbackend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikbackend.service.IUsuarioService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import static com.viii28stw.pensiltikbackend.util.RandomValue.randomAlphabetic;
import static com.viii28stw.pensiltikbackend.util.RandomValue.randomAlphanumeric;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UsuarioControllerTest {
    @Value("${basic.auth.user}")
    private String basicAuthUser;
    @Value("${basic.auth.password}")
    private String basicAuthPassword;
    @Value("${header.name}")
    private String headerName;
    
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private HttpHeaders httpHeaders;
    @Autowired
    private ObjectMapper mapper;
    @Value("${url.prefix}")
    private String urlPrefix;
    @Value("${url.login}")
    private String urlLogin;
    @Value("${url.search.user.by.id}")
    private String urlSearchUserById;
    @Value("${url.search.all.users}")
    private String urlSearchAllUsers;
    @Value("${url.save.user}")
    private String urlSaveUser;
    @Value("${url.update.user}")
    private String urlUpdateUser;
    @Value("${url.delete.user.by.id}")
    private String urlDeleteUserById;
    private static boolean INITIALIZED = false;

    @Before
    public void CriarUsuarioAdiministradorEFazerLogin() {
        if (!INITIALIZED) {
            UsuarioDto usuarioDto = usuarioService.salvarUsuario(UsuarioDto.builder()
                    .nome(randomAlphabetic(25))
                    .sobreNome(randomAlphabetic(25))
                    .email(randomAlphabetic(6).concat("@")
                            .concat(randomAlphabetic(4)).concat(".")
                            .concat(randomAlphabetic(2)))
                    .usuarioNivelAcesso(UsuarioNivelAcesso.ADMINISTRADOR)
                    .senha(randomAlphanumeric(8))
                    .sexo(Sexo.MASCULINO)
                    .dataNascimento(LocalDate.now())
                    .build());

            UsuarioDto usuarioDto1 = usuarioService.fazerLogin(usuarioDto);
            httpHeaders.add(headerName, usuarioDto1.getEmail());
            INITIALIZED = true;
        }
    }

    @Test
    public void salvarUsuarioSenhaNaoPodeTerTamanhoMaiorQue10() {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .senha(randomAlphanumeric(11))
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntityUsuario.getBody());
        then(responseEntityUsuario.getBody() instanceof DataIntegrityViolationException);
    }

    @Test
    public void salvarUsuarioNaoPodeRetornarNuloEnaoDeixarSalvarDoisUsuariosComEmailJaExistente() {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(10))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(responseEntityUsuario.getBody());
        then(responseEntityUsuario.getBody() instanceof UsuarioDto);

        ResponseEntity responseEntityUsuario1 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario1.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntityUsuario1.getBody());
        then(responseEntityUsuario1.getBody() instanceof IllegalArgumentException);
    }

    @Test
    public void atualizarUsuarioNaoPodeRetornarNuloEOUsuarioASerAtualizadoDeveConterID() throws IOException {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.OK);

        UsuarioDto usuarioDto1 = mapper.readValue(responseEntityUsuario.getBody().toString(), UsuarioDto.class);

        assertNotNull(usuarioDto1);

        ResponseEntity responseEntityLogin = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlLogin), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityLogin.getStatusCode()).isEqualTo(HttpStatus.OK);

        httpHeaders.add(headerName, usuarioDto1.getEmail());

        usuarioDto1.setNome(randomAlphabetic(25));
        usuarioDto1.setSobreNome(randomAlphabetic(25));
        usuarioDto1.setEmail(randomAlphabetic(7).concat("@")
                .concat(randomAlphabetic(5)).concat(".")
                .concat(randomAlphabetic(3)));
        usuarioDto1.setSenha(randomAlphanumeric(8));
        usuarioDto1.setUsuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM);
        usuarioDto1.setSexo(Sexo.FEMININO);
        usuarioDto1.setDataNascimento(LocalDate.now());

        ResponseEntity responseEntityUsuario1 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlUpdateUser), HttpMethod.PUT,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(responseEntityUsuario1.getBody());
        then(responseEntityUsuario1.getBody() instanceof UsuarioDto);


        ResponseEntity responseEntityUsuario2 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSearchUserById), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(responseEntityUsuario2.getBody());
        then(responseEntityUsuario2.getBody() instanceof UsuarioDto);

        UsuarioDto usuarioDto3 = mapper.readValue(responseEntityUsuario2.getBody().toString(), UsuarioDto.class);

        assertEquals(usuarioDto3, usuarioDto1);

        usuarioDto1.setCodigo(null);
        ResponseEntity responseEntityUsuario3 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlUpdateUser), HttpMethod.PUT,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario3.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntityUsuario3.getBody());
        then(responseEntityUsuario3.getBody() instanceof IllegalArgumentException);
    }

    @Test
    public void naoDeixarSalvarUsuarioSemNome() {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario.getBody());
        then(responseEntityUsuario.getBody() instanceof MethodArgumentNotValidException);
    }

    @Test
    public void naoDeixarSalvarUsuarioComNomeVazio() {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome("    ")
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario.getBody());
        then(responseEntityUsuario.getBody() instanceof MethodArgumentNotValidException);
    }

    @Test
    public void naoDeixarSalvarUsuarioSemSobrenome() {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario.getBody());
        then(responseEntityUsuario.getBody() instanceof MethodArgumentNotValidException);
    }

    @Test
    public void naoDeixarSalvarUsuarioComSobrenomeVazio() {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome("    ")
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario.getBody());
        then(responseEntityUsuario.getBody() instanceof MethodArgumentNotValidException);
    }

    @Test
    public void naoDeixarSalvarUsuarioSemEmail() {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario.getBody());
        then(responseEntityUsuario.getBody() instanceof MethodArgumentNotValidException);
    }

    @Test
    public void naoDeixarSalvarUsuarioComEmailNoFormatoErrado() {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email("@" + randomAlphabetic(5).concat(".").concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario.getBody());
        then(responseEntityUsuario.getBody() instanceof MethodArgumentNotValidException);


        usuarioDto.setEmail(randomAlphabetic(7));
        ResponseEntity responseEntityUsuario1 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario1.getBody());
        then(responseEntityUsuario1.getBody() instanceof MethodArgumentNotValidException);

        usuarioDto.setEmail(randomAlphabetic(7) + "@");
        ResponseEntity responseEntityUsuario2 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario2.getBody());
        then(responseEntityUsuario2.getBody() instanceof MethodArgumentNotValidException);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@").concat(randomAlphabetic(5)));
        ResponseEntity responseEntityUsuario3 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario3.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntityUsuario3.getBody());
        then(responseEntityUsuario3.getBody() instanceof IllegalArgumentException);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@").concat(".").concat(randomAlphabetic(3)));
        ResponseEntity responseEntityUsuario4 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario4.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario4.getBody());
        then(responseEntityUsuario4.getBody() instanceof MethodArgumentNotValidException);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@").concat(randomAlphabetic(5)).concat("."));
        ResponseEntity responseEntityUsuario5 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario5.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario5.getBody());
        then(responseEntityUsuario5.getBody() instanceof MethodArgumentNotValidException);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@")
                .concat(randomAlphabetic(5)).concat(".")
                .concat(randomAlphabetic(1)));
        ResponseEntity responseEntityUsuario6 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario6.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntityUsuario6.getBody());
        then(responseEntityUsuario6.getBody() instanceof IllegalArgumentException);

        usuarioDto.setEmail(randomAlphabetic(7).concat("@")
                .concat(randomAlphabetic(5)).concat(".")
                .concat(randomAlphabetic(8)));
        ResponseEntity responseEntityUsuario7 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario7.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntityUsuario7.getBody());
        then(responseEntityUsuario7.getBody() instanceof IllegalArgumentException);

        usuarioDto.setEmail(randomAlphabetic(7) + randomAlphabetic(5) + "." + randomAlphabetic(8));
        ResponseEntity responseEntityUsuario8 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario8.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario8.getBody());
        then(responseEntityUsuario8.getBody() instanceof MethodArgumentNotValidException);

    }

    @Test
    public void naoDeixarSalvarUsuarioSemSexo() {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat(randomAlphabetic(5)).concat(".").concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntityUsuario.getBody());
        then(responseEntityUsuario.getBody() instanceof MethodArgumentNotValidException);
    }

    @Test
    public void buscarUsuarioPorIdNaoPodeRetornarNulo() throws IOException {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.OK);

        UsuarioDto usuarioDto1 = mapper.readValue(responseEntityUsuario.getBody().toString(), UsuarioDto.class);

        assertNotNull(usuarioDto1);

        ResponseEntity responseEntityUsuario2 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSearchUserById), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario2.getStatusCode()).isEqualTo(HttpStatus.OK);

        UsuarioDto usuarioDto2 = mapper.readValue(responseEntityUsuario2.getBody().toString(), UsuarioDto.class);

        assertNotNull(usuarioDto2);
        assertEquals(usuarioDto2, usuarioDto1);

        ResponseEntity responseEntityUsuario3 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlDeleteUserById), HttpMethod.DELETE,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario3.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity responseEntityUsuario4 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSearchUserById), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario4.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        then(responseEntityUsuario4.getBody() instanceof NoSuchElementException);
    }

    @Test
    public void buscarTodosOsUsuarios() throws IOException {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.OK);

        UsuarioDto usuarioDto1 = mapper.readValue(responseEntityUsuario.getBody().toString(), UsuarioDto.class);

        assertNotNull(usuarioDto1);

        ResponseEntity<UsuarioDto[]> responseEntityUsuarios = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSearchAllUsers), HttpMethod.GET,
                        new HttpEntity<>(httpHeaders), UsuarioDto[].class);

        then(responseEntityUsuarios.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<UsuarioDto> listusuarioDto = Arrays.asList(responseEntityUsuarios.getBody());

        assertNotNull(listusuarioDto);
        then(listusuarioDto.isEmpty());
        assertNotNull(listusuarioDto.get(0));
        then(listusuarioDto.get(0) instanceof UsuarioDto);
    }

    @Test
    public void deletarUsuarioPorId() throws IOException {
        @SuppressWarnings("rawtypes")
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.OK);

        UsuarioDto usuarioDto1 = mapper.readValue(responseEntityUsuario.getBody().toString(), UsuarioDto.class);

        assertNotNull(usuarioDto1);

        ResponseEntity responseEntityUsuario2 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlDeleteUserById), HttpMethod.DELETE,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario2.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity responseEntityUsuario3 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSearchUserById), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario3.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        then(responseEntityUsuario3.getBody() instanceof NoSuchElementException);
    }

    @Test
    public void deletarUsuarioPorIdInexistente() throws IOException {
        @SuppressWarnings("rawtypes")
        HttpEntity request = new HttpEntity<>(httpHeaders);

        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.USUARIO_COMUM)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.OK);

        UsuarioDto usuarioDto1 = mapper.readValue(responseEntityUsuario.getBody().toString(), UsuarioDto.class);

        assertNotNull(usuarioDto1);

        ResponseEntity responseEntityUsuario2 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlDeleteUserById), HttpMethod.DELETE,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario2.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity responseEntityUsuario3 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlDeleteUserById), HttpMethod.DELETE,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario3.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        then(responseEntityUsuario3.getBody() instanceof EmptyResultDataAccessException);
    }

    @Test
    public void fazerLogin() throws IOException {
        @SuppressWarnings("rawtypes")

        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(randomAlphabetic(25))
                .sobreNome(randomAlphabetic(25))
                .email(randomAlphabetic(7).concat("@")
                        .concat(randomAlphabetic(5)).concat(".")
                        .concat(randomAlphabetic(3)))
                .senha(randomAlphanumeric(8))
                .usuarioNivelAcesso(UsuarioNivelAcesso.ADMINISTRADOR)
                .sexo(Sexo.MASCULINO)
                .dataNascimento(LocalDate.now())
                .build();

        ResponseEntity responseEntityUsuario = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlSaveUser), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        then(responseEntityUsuario.getStatusCode()).isEqualTo(HttpStatus.OK);

        UsuarioDto usuarioDto1 = mapper.readValue(responseEntityUsuario.getBody().toString(), UsuarioDto.class);

        assertNotNull(usuarioDto1);

        UsuarioDto usDto = UsuarioDto.builder()
                .email(usuarioDto1.getEmail())
                .senha(usuarioDto.getSenha())
                .build();

        ResponseEntity responseEntityUsuario2 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlLogin), HttpMethod.POST,
                        new HttpEntity<>(usDto, httpHeaders), String.class);

        then(responseEntityUsuario2.getStatusCode()).isEqualTo(HttpStatus.OK);

        UsuarioDto usuarioDto2 = mapper.readValue(responseEntityUsuario2.getBody().toString(), UsuarioDto.class);

        assertNotNull(usuarioDto2);
        assertEquals(usuarioDto2, usuarioDto1);

        ResponseEntity responseEntityUsuario3 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlDeleteUserById), HttpMethod.DELETE,
                        new HttpEntity<>(usuarioDto1, httpHeaders), String.class);

        then(responseEntityUsuario3.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity responseEntityUsuario4 = testRestTemplate
                .withBasicAuth(basicAuthUser, basicAuthPassword)
                .exchange(urlPrefix.concat(urlLogin), HttpMethod.POST,
                        new HttpEntity<>(usDto, httpHeaders), String.class);

        assertNull(responseEntityUsuario4.getBody());
        then(responseEntityUsuario4.getBody() instanceof EmptyResultDataAccessException);
    }

}
