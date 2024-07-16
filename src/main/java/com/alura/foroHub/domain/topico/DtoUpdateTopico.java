package com.alura.foroHub.domain.topico;


public record DtoUpdateTopico(
        String titulo,
        String mensaje,
        String nombreCurso,
        Long usuario_id,
        String estado
) {
}
