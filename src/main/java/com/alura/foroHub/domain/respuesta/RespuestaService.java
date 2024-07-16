package com.alura.foroHub.domain.respuesta;

import com.alura.foroHub.domain.topico.TopicoRepository;
import com.alura.foroHub.infra.errores.IntegrityValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import com.alura.foroHub.domain.topico.DtoListTopico;
import com.alura.foroHub.domain.topico.Topico;
import com.alura.foroHub.domain.usuario.Usuario;
import com.alura.foroHub.domain.usuario.UsuarioRepository;

import java.net.URI;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public ResponseEntity<DtoResponseRespuesta> registrarRespuesta(DtoRegisterRespuesta dtoRegisterRespuesta,
                                                                   UriComponentsBuilder uriComponentsBuilder) {

        if (topicoRepository.findById(dtoRegisterRespuesta.topico_id()).isEmpty()){
            throw new IntegrityValidate("El tópico de la respuesta no fue encontrado. Revise el id.");
        }

        if (usuarioRepository.findById(dtoRegisterRespuesta.usuario_id()).isEmpty()){
            throw new IntegrityValidate("El autor de la respuesta no fue encontrado. Revise el id.");
        }

        Topico topico = topicoRepository.getReferenceById(dtoRegisterRespuesta.topico_id());
        Usuario autor = usuarioRepository.getReferenceById(dtoRegisterRespuesta.usuario_id());
        Respuesta respuesta = new Respuesta(dtoRegisterRespuesta, topico, autor);
        Respuesta respuestaRet = respuestaRepository.save(respuesta);

        DtoResponseRespuesta dtoResponseRespuesta = new DtoResponseRespuesta(respuestaRet.getId(), respuestaRet.getMensaje(),
                respuestaRet.getFecha_creacion(), new DtoListTopico(respuestaRet.getTopico()),
                respuestaRet.getAutor().getNombre(), respuestaRet.getSolucion());

        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuestaRet.getId()).toUri();

        return ResponseEntity.created(url).body(dtoResponseRespuesta);

    }

    public ResponseEntity<DtoResponseRespuesta> actualizarRespuesta(DtoUpdateRespuesta dtoUpdateRespuesta,
                                                                    Long id, UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = null;
        Usuario usuario = null;
        if (respuestaRepository.findById(id).isEmpty()){
            throw new IntegrityValidate("La respuesta no fue encontrada. Verifique el id.");
        }

        if (dtoUpdateRespuesta.topico_id() != null){
            if (topicoRepository.findById(dtoUpdateRespuesta.topico_id()).isEmpty()){
                throw new IntegrityValidate("El tópico de la respuesta no fue encontrado. Verifique el id.");
            }
            topico = topicoRepository.findById(dtoUpdateRespuesta.topico_id()).get();
        }

        if (dtoUpdateRespuesta.usuario_id() != null){
            if (usuarioRepository.findById(dtoUpdateRespuesta.usuario_id()).isEmpty()){
                throw new IntegrityValidate("El usuario de la respuesta no fue encontrado. Verifique el id.");
            }
            usuario = usuarioRepository.findById(dtoUpdateRespuesta.usuario_id()).get();
        }

        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarDatos(dtoUpdateRespuesta, topico, usuario);

        DtoResponseRespuesta dtoResponseRespuesta = new DtoResponseRespuesta(respuesta.getId(), respuesta.getMensaje(),
        respuesta.getFecha_creacion(), new DtoListTopico(respuesta.getTopico()), respuesta.getAutor().getNombre(), respuesta.getSolucion());

        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();

        return ResponseEntity.created(url).body(dtoResponseRespuesta);
    }

    public ResponseEntity<Page> listarRespuestas(Pageable paginacion) {
        return ResponseEntity.ok(respuestaRepository.listarRespuestas(paginacion)
                .map(DtoListRespuesta::new));
    }


    public ResponseEntity eliminarRespuesta(Long id) {

        if (respuestaRepository.findById(id).isEmpty()){
            throw new IntegrityValidate("La respuesta no fue encontrada. Verifique el id.");
        }

        respuestaRepository.borrarRespuesta(id);

        return ResponseEntity.noContent().build();
    }

}
