package com.alura.foroHub.domain.respuesta;


import com.alura.foroHub.domain.topico.DtoListTopico;

import java.time.LocalDateTime;

public record DtoListRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fecha_creacion,
        String autor,
        Boolean solucion,
        DtoListTopico topico
) {
    public DtoListRespuesta(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFecha_creacion(),
                respuesta.getAutor().getNombre(), respuesta.getSolucion(),
                new DtoListTopico(respuesta.getTopico()));
    }
}
