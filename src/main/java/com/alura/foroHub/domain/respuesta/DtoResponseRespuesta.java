package com.alura.foroHub.domain.respuesta;

import com.alura.foroHub.domain.topico.DtoListTopico;

import java.time.LocalDateTime;

public record DtoResponseRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fecha_creacion,
        DtoListTopico topico,
        String autor,
        Boolean solucion
) {
}
