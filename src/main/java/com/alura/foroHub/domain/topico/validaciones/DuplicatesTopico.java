package com.alura.foroHub.domain.topico.validaciones;

import com.alura.foroHub.domain.topico.DtoRegisterTopico;
import com.alura.foroHub.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alura.foroHub.domain.topico.Topico;

import java.util.List;

@Component
public class DuplicatesTopico implements ValidadorTopicos {

    @Autowired
    public TopicoRepository topicoRepository;

    public void validar(DtoRegisterTopico dtoRegisterTopico){

        List<Topico> topicos = topicoRepository.findByTitulo(dtoRegisterTopico.titulo());
        topicos.stream()
                .filter(t -> t.getMensaje().equalsIgnoreCase(dtoRegisterTopico.mensaje()))
                .findFirst()
                .ifPresent(t -> {
                    throw new ValidationException("No se pueden crear tópicos duplicados");
                });

    }

}
