package com.alura.foroHub.domain.usuario;


public record DtoUpdateUsuario(
        String nombre,
        String email,
        String clave,
        String perfil
) {
}
