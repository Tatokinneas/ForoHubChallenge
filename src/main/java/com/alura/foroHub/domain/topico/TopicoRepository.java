package com.alura.foroHub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {


    List<Topico> findByTitulo(String titulo);

    @Query("SELECT t FROM Topico t ORDER BY t.fechaCreacion ASC")
    Page<Topico> listarTopicos(Pageable paginacion);

    @Modifying
    @Query("DELETE FROM Topico t WHERE t.id=:idTopico")
    void borrarTopico(Long idTopico);
}
