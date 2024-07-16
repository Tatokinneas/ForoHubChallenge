package com.alura.foroHub.domain.topico;

import com.alura.foroHub.domain.curso.Curso;
import com.alura.foroHub.domain.respuesta.Respuesta;
import com.alura.foroHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    Usuario autor;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    List<Respuesta> respuestas;

    public Topico(DtoRegisterTopico dtoRegisterTopico) {
        this.titulo = dtoRegisterTopico.titulo();
        this.mensaje = dtoRegisterTopico.mensaje();

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String horaFormateada = ahora.format(formateador);
        this.fechaCreacion = LocalDateTime.parse(horaFormateada, formateador);

        this.estado = Estado.PENDIENTE;

    }

    public void actualizarDatos(DtoUpdateTopico dtoUpdateTopico, Curso curso, Usuario autor){
        if (dtoUpdateTopico.titulo() != null) {
            this.titulo = dtoUpdateTopico.titulo();
        }
        if (dtoUpdateTopico.mensaje() != null){
            this.mensaje = dtoUpdateTopico.mensaje();
        }
        if (curso != null){
            this.curso = curso;
        }
        if (autor != null){
            this.autor = autor;
        }
        if (dtoUpdateTopico.estado() != null){
            this.estado = Estado.fromString(dtoUpdateTopico.estado());
        }
    }
}
