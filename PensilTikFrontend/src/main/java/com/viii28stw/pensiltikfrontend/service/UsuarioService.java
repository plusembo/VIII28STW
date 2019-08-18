package com.viii28stw.pensiltikfrontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viii28stw.pensiltikfrontend.model.domain.Sessao;
import com.viii28stw.pensiltikfrontend.model.domain.Usuario;
import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.List;

@Component
public class UsuarioService implements IUsuarioService {
    @Value("${basic.auth.user}")
    private String basicAuthUser;
    @Value("${basic.auth.password}")
    private String basicAuthPassword;
    @Value("${url.prefix}")
    private String urlPrefix;
    @Value("${url.login}")
    private String urlLogin;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private HttpHeaders httpHeaders;

    public UsuarioDto buscarUsuarioMaiorCodigo() {
        return null;
    }

    @Override
    public UsuarioDto buscarUsuarioPorId(String id) {
        return null;
    }

    @Override
    public List<UsuarioDto> buscarTodosOsUsuarios() {
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
    public boolean deletarUsuarioPorId(String id) {
        return true;
    }

    @Override
    public UsuarioDto login(String email, String password) {
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .email(email)
                .senha(password)
                .build();
        RestTemplate restTemplate = restTemplateBuilder.basicAuthentication(basicAuthUser, basicAuthPassword).build();

        ResponseEntity responseEntityUsuario = restTemplate
                .exchange(urlPrefix.concat(urlLogin), HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);
        try {
            if (responseEntityUsuario.getBody() != null) {
                UsuarioDto usuarioDto1 = mapper.readValue(responseEntityUsuario.getBody().toString(), UsuarioDto.class);
                Usuario usuario = Usuario.builder()
                        .codigo(usuarioDto1.getCodigo())
                        .nome(usuarioDto1.getNome())
                        .sobreNome(usuarioDto1.getSobreNome())
                        .email(usuarioDto1.getEmail())
                        .senha(usuarioDto1.getSenha())
                        .sexo(usuarioDto1.getSexo())
                        .dataNascimento(usuarioDto1.getDataNascimento())
                        .build();

                Sessao.getInstance().setUsuario(usuario);
                Sessao.getInstance().setLogoutRequest(false);

                return usuarioDto;
            }
        } catch (IOException e) {
        }
        return null;
    }

    @Override
    public void logout(String email) {
    }

}
