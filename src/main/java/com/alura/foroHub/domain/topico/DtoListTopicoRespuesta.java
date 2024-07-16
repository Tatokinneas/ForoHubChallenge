package com.alura.foroHub.domain.topico;

import com.alura.foroHub.domain.respuesta.DtoListRespuestaTopico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DtoListTopicoRespuesta(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha_creacion,
        String estado,
        String curso,
        String autor,
        List<DtoListRespuestaTopico> respuestas
) {

    public DtoListTopicoRespuesta(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),
                topico.getEstado().toString(), topico.getCurso().getNombre(), topico.getAutor().getNombre(),
                topico.getRespuestas().stream()
                        .map(r -> new DtoListRespuestaTopico(r))
                        .collect(Collectors.toList()));
    }


}
