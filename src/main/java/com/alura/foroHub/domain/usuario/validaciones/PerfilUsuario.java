package com.alura.foroHub.domain.usuario.validaciones;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import com.alura.foroHub.domain.usuario.DtoRegisterUsuario;
import com.alura.foroHub.domain.usuario.Perfil;

@Component
public class PerfilUsuario implements ValidateUsuarios {

    @Override
    public void validar(DtoRegisterUsuario dtoRegisterUsuario) {

        if (!dtoRegisterUsuario.perfil().equalsIgnoreCase(String.valueOf(Perfil.USUARIO))){
            throw new ValidationException("Sólo se puede registrar como Usuario");
        }
    }
}
