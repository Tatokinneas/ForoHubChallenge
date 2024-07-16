package com.alura.foroHub.controller;

import com.alura.foroHub.domain.respuesta.*;
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
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private RespuestaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DtoResponseRespuesta> registrarRespuesta(@RequestBody @Valid DtoRegisterRespuesta dtoRegisterRespuesta, UriComponentsBuilder uriComponentsBuilder){

        return  service.registrarRespuesta(dtoRegisterRespuesta, uriComponentsBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DtoResponseRespuesta> actualizaRespuesta(@RequestBody @Valid DtoUpdateRespuesta dtoUpdateRespuesta,
                                                                   @PathVariable Long id, UriComponentsBuilder uriComponentsBuilder){

        return service.actualizarRespuesta(dtoUpdateRespuesta, id, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page> listadoRespuesta(@PageableDefault(size = 10) Pageable paginacion){

        return service.listarRespuestas(paginacion);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){

        return service.eliminarRespuesta(id);
    }
}
