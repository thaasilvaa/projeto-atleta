package com.api.senai.repository;

import com.api.senai.entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtletaRepository extends JpaRepository<Atleta, Long> {

    boolean existsByNomeIgnoreCaseAndModalidade(
            String nome,
            String modalidade
    );

    boolean existsByNomeIgnoreCaseAndModalidadeAndIdNot(
            String nome,
            String modalidade,
            Long id
    );
}