package com.alura.foroHub.domain.respuesta;

import java.time.LocalDateTime;

public record DtoListRespuestaTopico(
        Long id,
        String mensaje,
        LocalDateTime fecha_creacion,
        String autor,
        Boolean solucion
) {
    public DtoListRespuestaTopico(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFecha_creacion(),
                respuesta.getAutor().getNombre(), respuesta.getSolucion());
    }
}
