package com.alura.foroHub.domain.topico;

public record DtoRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String fecha_creacion,
        String estado,
        Long curso_id,
        Long usuario_id
) {
}
