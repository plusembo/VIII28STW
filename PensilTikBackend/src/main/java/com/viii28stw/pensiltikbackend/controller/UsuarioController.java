package com.viii28stw.pensiltikbackend.controller;

import com.viii28stw.pensiltikbackend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikbackend.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/pensiltik")
public class UsuarioController {

    private final static String USER_LOGGED_IN = "user_logged_in";

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/buscarusuarioporid")
    public ResponseEntity<UsuarioDto> buscarUsuarioPorId(@RequestHeader HashMap<String, Object> headers,
                                                         @RequestBody @Valid UsuarioDto usuarioIdDto){
        if(!usuarioService.isUserLoggedIn(String.valueOf(headers.get(USER_LOGGED_IN)))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        UsuarioDto usuarioDto = usuarioService.buscarUsuarioPorId(usuarioIdDto.getCodigo());
        return new ResponseEntity<>(usuarioDto, HttpStatus.OK);
    }

    @GetMapping("/buscartodososusuarios")
    public ResponseEntity<List<UsuarioDto>> buscarTodosOsUsuarios(@RequestHeader HashMap<String, Object> headers) {
        if(!usuarioService.isUserLoggedIn(String.valueOf(headers.get(USER_LOGGED_IN)))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        List<UsuarioDto> usuariosDto = usuarioService.buscarTodosOsUsuarios();
        return new ResponseEntity<>(usuariosDto, HttpStatus.OK);
    }

    @PostMapping("/salvarusuario")
    public ResponseEntity<UsuarioDto> salvarUsuario(@RequestHeader HashMap<String, Object> headers,
                                                    @RequestBody @Valid UsuarioDto usuarioDto) {
        if(!usuarioService.isUserLoggedIn(String.valueOf(headers.get(USER_LOGGED_IN)))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(usuarioService.salvarUsuario(usuarioDto), HttpStatus.OK);
    }

    @PutMapping("/atualizarusuario")
    public ResponseEntity<UsuarioDto> atualizarUsuario(@RequestHeader HashMap<String, Object> headers,
                                                       @RequestBody @Valid UsuarioDto usuarioDto) {
        if(!usuarioService.isUserLoggedIn(String.valueOf(headers.get(USER_LOGGED_IN)))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(usuarioService.atualizarUsuario(usuarioDto), HttpStatus.OK);
    }

    @DeleteMapping("/deletarusuarioporid")
    public ResponseEntity<Boolean> deletarUsuarioPorId(@RequestHeader HashMap<String, Object> headers,
                                                       @RequestBody @Valid UsuarioDto usuarioDto) {
        if(!usuarioService.isUserLoggedIn(String.valueOf(headers.get(USER_LOGGED_IN)))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(usuarioService.deletarUsuarioPorId(usuarioDto.getCodigo()), HttpStatus.OK);
    }

    @PostMapping("/fazerlogin")
    public ResponseEntity<UsuarioDto> login(@RequestBody @Valid UsuarioDto usuarioDto) {
        return new ResponseEntity<>(usuarioService.fazerLogin(usuarioDto), HttpStatus.OK);
    }

}
