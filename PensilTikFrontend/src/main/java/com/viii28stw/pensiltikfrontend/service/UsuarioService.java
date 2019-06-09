package com.viii28stw.pensiltikfrontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikfrontend.util.BasicAuth;
import com.viii28stw.pensiltikfrontend.util.UrlPrefixFactory;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
public class UsuarioService implements IUsuarioService {

    private static final String FAZER_LOGIN = "/fazerlogin/";
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
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();

        UsuarioDto usuarioDto = UsuarioDto.builder()
                .email(email)
                .senha(senha)
                .build();
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.basicAuthentication(BasicAuth.getUser(), BasicAuth.getPassword()).build();


        ResponseEntity responseEntityUsuario = restTemplate
                .exchange(UrlPrefixFactory.getUrlPrefix() + FAZER_LOGIN, HttpMethod.POST,
                        new HttpEntity<>(usuarioDto, httpHeaders), String.class);

        try {
            UsuarioDto usuarioDto1 = mapper.readValue(responseEntityUsuario.getBody().toString(), UsuarioDto.class);

            System.out.println(usuarioDto1);
        } catch (IOException e) {
        }





        /*if (usuarioDto != null) {
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
        */
        usuarioDto = UsuarioDto.builder()
                .email("plam.l@live.fr")
                .senha("kjhdfnkl")
                .build();


        return usuarioDto;
    }

}
