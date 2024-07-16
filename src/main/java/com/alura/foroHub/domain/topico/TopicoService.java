package com.alura.foroHub.domain.topico;

import com.alura.foroHub.domain.curso.Curso;
import com.alura.foroHub.domain.curso.CursoRepository;
import com.alura.foroHub.domain.topico.validaciones.ValidadorTopicos;
import com.alura.foroHub.domain.usuario.Usuario;
import com.alura.foroHub.domain.usuario.UsuarioRepository;
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
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    List<ValidadorTopicos> validadores;

    public ResponseEntity<DtoRespuestaTopico> registrar(DtoRegisterTopico dtoRegisterTopico, UriComponentsBuilder uriComponentsBuilder){

        if (cursoRepository.findByNombreContainsIgnoreCase(dtoRegisterTopico.nombreCurso()).isEmpty()){
            throw new IntegrityValidate("El curso no fue encontrado");
        }

        if (usuarioRepository.findById(dtoRegisterTopico.usuario_id()).isEmpty()){
            throw new IntegrityValidate("El usuario no fue encontrado");
        }

        validadores.forEach(v -> v.validar(dtoRegisterTopico));

        var topico = new Topico(dtoRegisterTopico);
        topico.setCurso(cursoRepository.findByNombreContainsIgnoreCase(dtoRegisterTopico.nombreCurso()).get());
        topico.setAutor(usuarioRepository.findById(dtoRegisterTopico.usuario_id()).get());
        Topico topicoRet = topicoRepository.save(topico);

        DtoRespuestaTopico dtoRespuestaTopico = new DtoRespuestaTopico(topicoRet.getId(), topicoRet.getTitulo(),
                topicoRet.getMensaje(), topicoRet.getFechaCreacion().toString(), topicoRet.getEstado().toString(),
                topicoRet.getCurso().getId(), topicoRet.getAutor().getId());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoRet.getId()).toUri();

        return ResponseEntity.created(url).body(dtoRespuestaTopico);
    }

    public ResponseEntity<Page> listarTopicos(Pageable paginacion){

        return ResponseEntity.ok(topicoRepository.listarTopicos(paginacion)
                .map(DtoListTopicoRespuesta::new));
    }

    public ResponseEntity<DtoListTopicoRespuesta> listarDetalleTopicos(Long id){

        if (topicoRepository.findById(id).isEmpty()){
            throw new IntegrityValidate("El tópico no existe.");
        }

        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DtoListTopicoRespuesta(topico);

        return ResponseEntity.ok(datosTopico);
    }


    public ResponseEntity<DtoRespuestaTopico> actualizarTopico(DtoUpdateTopico dtoUpdateTopico,
                                                               Long id, UriComponentsBuilder uriComponentsBuilder) {

        Curso curso = null;
        Usuario usuario = null;

        if (topicoRepository.findById(id).isEmpty()){
            throw new IntegrityValidate("El tópico no fue encontrado. Verifique el id.");
        }

        if (dtoUpdateTopico.nombreCurso() != null) {
            if (cursoRepository.findByNombreContainsIgnoreCase(dtoUpdateTopico.nombreCurso()).isEmpty()) {
                throw new IntegrityValidate("El curso no fue encontrado");
            }
            curso = cursoRepository.findByNombreContainsIgnoreCase(dtoUpdateTopico.nombreCurso()).get();
        }

        if (dtoUpdateTopico.usuario_id() != null) {
            if (usuarioRepository.findById(dtoUpdateTopico.usuario_id()).isEmpty()) {
                throw new IntegrityValidate("El usuario no fue encontrado");
            }
            usuario = usuarioRepository.findById(dtoUpdateTopico.usuario_id()).get();
        }

        Topico topico = topicoRepository.getReferenceById(id);

        DtoRegisterTopico dtoRegisterTopico = new DtoRegisterTopico(dtoUpdateTopico.titulo(),
                dtoUpdateTopico.mensaje(), dtoUpdateTopico.nombreCurso(),
                dtoUpdateTopico.usuario_id());

        validadores.forEach(v -> v.validar(dtoRegisterTopico));

        topico.actualizarDatos(dtoUpdateTopico, curso, usuario);

        DtoRespuestaTopico dtoRespuestaTopico = new DtoRespuestaTopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion().toString(), topico.getEstado().toString(),
                topico.getCurso().getId(), topico.getAutor().getId());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(dtoRespuestaTopico);

    }


    public ResponseEntity eliminarTopico(Long id) {

        if (topicoRepository.findById(id).isEmpty()){
            throw new IntegrityValidate("El tópico no fue encontrado. Verifique el id.");
        }

        topicoRepository.borrarTopico(id);

        return ResponseEntity.noContent().build();
    }
}
