package com.alura.foroHub.domain.respuesta;

import com.alura.foroHub.domain.topico.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.alura.foroHub.domain.topico.Topico;
import com.alura.foroHub.domain.usuario.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private LocalDateTime fecha_creacion;
    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    Usuario autor;
    private Boolean solucion;

    public Respuesta(DtoRegisterRespuesta dtoRegisterRespuesta, Topico topico, Usuario autor) {
        this.mensaje = dtoRegisterRespuesta.mensaje();

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String horaFormateada = ahora.format(formateador);
        this.fecha_creacion = LocalDateTime.parse(horaFormateada, formateador);

        this.topico = topico;
        this.autor = autor;
        this.solucion = false;
    }

    public void actualizarDatos(DtoUpdateRespuesta dtoUpdateRespuesta, Topico topico, Usuario autor) {
        if (dtoUpdateRespuesta.mensaje() != null){
            this.mensaje = dtoUpdateRespuesta.mensaje();
        }
        if (topico != null){
            this.topico = topico;
        }
        if (autor != null){
            this.autor = autor;
        }
        if (dtoUpdateRespuesta.solucion() != null){
            if (dtoUpdateRespuesta.solucion()){
                this.solucion = true;
                this.topico.setEstado(Estado.POSTEADO);
            }else{
                this.solucion = false;
            }
        }
    }
}
