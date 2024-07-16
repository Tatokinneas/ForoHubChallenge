package com.alura.foroHub.domain.usuario;

import com.alura.foroHub.domain.usuario.validaciones.ValidateUsuarios;
import com.alura.foroHub.infra.errores.IntegrityValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    List<ValidateUsuarios> validadores;

    public ResponseEntity<DtoResponseUsuario> registarUsuario(DtoRegisterUsuario dtoRegisterUsuario,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        var usuario = new Usuario(dtoRegisterUsuario);
        validadores.forEach(v -> v.validar(dtoRegisterUsuario));
        Usuario usuarioConId = usuarioRepository.save(usuario);

        DtoResponseUsuario dtoResponseUsuario = new DtoResponseUsuario(usuarioConId);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuarioConId.getId()).toUri();

        return ResponseEntity.created(url).body(dtoResponseUsuario);
    }

    public ResponseEntity<DtoResponseUsuario> actualizarUsuario(DtoUpdateUsuario dtoUpdateUsuario, Long id, UriComponentsBuilder uriComponentsBuilder) {

        if (usuarioRepository.findById(id).isEmpty()){
            throw new IntegrityValidate("El usuario no fue encontrado. Verifique el id.");
        }

        Usuario usuario = usuarioRepository.getReferenceById(id);

        DtoRegisterUsuario dtoRegisterUsuario = realizarCopiaActualizado(usuario, dtoUpdateUsuario);

        validadores.forEach(v -> v.validar(dtoRegisterUsuario));

        if (dtoUpdateUsuario.nombre() != null){
            usuario.setNombre(dtoUpdateUsuario.nombre());
        }
        if (dtoUpdateUsuario.email() != null){
            usuario.setEmail(dtoUpdateUsuario.email());
        }
        if (dtoUpdateUsuario.clave() != null){
            usuario.setClave(dtoUpdateUsuario.clave());
        }
        if (dtoUpdateUsuario.perfil() != null){
            usuario.setPerfil(Perfil.fromString(dtoUpdateUsuario.perfil()));
        }

        DtoResponseUsuario dtoResponseUsuario = new DtoResponseUsuario(usuario);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(url).body(dtoResponseUsuario);

    }

    public ResponseEntity<Page> listarUsuarios(Pageable paginacion) {

        return ResponseEntity.ok(usuarioRepository.listarUsuarios(paginacion)
                .map(DtoResponseUsuario::new));
    }

    public ResponseEntity eliminarUsuario(Long id) {

        if (usuarioRepository.findById(id).isEmpty()){
            throw new IntegrityValidate("El usuario no fue encontrado. Verifique el id.");
        }

        usuarioRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private DtoRegisterUsuario realizarCopiaActualizado(Usuario usuario, DtoUpdateUsuario dtoUpdateUsuario){
        String nombre = usuario.getNombre();
        String email = usuario.getEmail();
        String clave = usuario.getClave();
        String perfil = usuario.getPerfil().toString();

        if (dtoUpdateUsuario.nombre() != null){
            nombre = dtoUpdateUsuario.nombre();
        }
        if (dtoUpdateUsuario.email() != null){
            email = dtoUpdateUsuario.email();
        }
        if (dtoUpdateUsuario.clave() != null){
            clave = dtoUpdateUsuario.clave();
        }
        if (dtoUpdateUsuario.perfil() != null){
            perfil = dtoUpdateUsuario.perfil();
        }

        DtoRegisterUsuario dtoRegisterUsuario = new DtoRegisterUsuario(nombre, email, clave, perfil);
        return dtoRegisterUsuario;
    }

}
