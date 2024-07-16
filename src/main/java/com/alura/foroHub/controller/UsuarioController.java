package com.alura.foroHub.controller;

import com.alura.foroHub.domain.usuario.DtoUpdateUsuario;
import com.alura.foroHub.domain.usuario.DtoRegisterUsuario;
import com.alura.foroHub.domain.usuario.DtoResponseUsuario;
import com.alura.foroHub.domain.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DtoResponseUsuario> registrarUsuario(@RequestBody @Valid DtoRegisterUsuario dtoRegisterUsuario, UriComponentsBuilder uriComponentsBuilder){
        return service.registarUsuario(dtoRegisterUsuario, uriComponentsBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DtoResponseUsuario> actualizarUsuario(@RequestBody DtoUpdateUsuario dtoUpdateUsuario, @PathVariable Long id, UriComponentsBuilder uriComponentsBuilder){
        return service.actualizarUsuario(dtoUpdateUsuario, id, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page> listadoUsuarios(@PageableDefault(size = 10) Pageable paginacion){

        return service.listarUsuarios(paginacion);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id){

        return service.eliminarUsuario(id);
    }

}
