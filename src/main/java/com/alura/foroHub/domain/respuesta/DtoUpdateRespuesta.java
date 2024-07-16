package com.alura.foroHub.domain.respuesta;

public record DtoUpdateRespuesta(
        String mensaje,
        Long topico_id,
        Long usuario_id,
        Boolean solucion
) {
}
