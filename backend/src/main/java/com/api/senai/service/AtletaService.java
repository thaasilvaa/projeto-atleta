package com.api.senai.service;

import com.api.senai.entity.Atleta;
import com.api.senai.repository.AtletaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtletaService {

    private final AtletaRepository atletaRepository;

    public AtletaService(AtletaRepository atletaRepository) {
        this.atletaRepository = atletaRepository;
    }


    public List<Atleta> listarTodos() {

        return atletaRepository.findAll();
    }
    public Atleta buscarPorId(Long id) {

        return atletaRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Atleta não encontrado."
                    )
                );
    }

    public Atleta cadastrar(Atleta atleta) {

        System.out.println("========== CADASTRANDO ATLETA ==========");

        System.out.println(
            "Nome: " + atleta.getNome()
        );

        System.out.println(
            "Modalidade: " + atleta.getModalidade()
        );

        System.out.println(
            "Idade: " + atleta.getIdade()
        );

        System.out.println(
            "Salário: " + atleta.getSalarioMensal()
        );


        // Valida as regras de negócio
        validarAtleta(atleta);


        // Verifica nome duplicado
        boolean nomeDuplicado =
                atletaRepository
                    .existsByNomeIgnoreCaseAndModalidade(
                        atleta.getNome(),
                        atleta.getModalidade()
                    );


        if (nomeDuplicado) {

            throw new RuntimeException(
                "Já existe um atleta com esse nome nessa modalidade."
            );
        }


        // Salva no banco
        Atleta atletaSalvo =
                atletaRepository.save(atleta);


        System.out.println(
            "ID GERADO: " + atletaSalvo.getId()
        );

        System.out.println(
            "========================================"
        );


        return atletaSalvo;
    }

    public Atleta atualizar(
            Long id,
            Atleta atletaAtualizado) {


        // Verifica se existe
        Atleta atletaExistente =
                buscarPorId(id);


        // Valida as regras
        validarAtleta(atletaAtualizado);


        // Verifica duplicidade
        boolean nomeDuplicado =
                atletaRepository
                    .existsByNomeIgnoreCaseAndModalidadeAndIdNot(
                        atletaAtualizado.getNome(),
                        atletaAtualizado.getModalidade(),
                        id
                    );


        if (nomeDuplicado) {

            throw new RuntimeException(
                "Já existe um atleta com esse nome nessa modalidade."
            );
        }


        // Atualiza os dados

        atletaExistente.setNome(
            atletaAtualizado.getNome()
        );

        atletaExistente.setModalidade(
            atletaAtualizado.getModalidade()
        );

        atletaExistente.setIdade(
            atletaAtualizado.getIdade()
        );

        atletaExistente.setSalarioMensal(
            atletaAtualizado.getSalarioMensal()
        );


        return atletaRepository.save(
            atletaExistente
        );
    }

    public void excluir(Long id) {

        Atleta atleta =
                buscarPorId(id);

        atletaRepository.delete(atleta);
    }


    // ==============================
    // VALIDAÇÕES
    // ==============================

    private void validarAtleta(Atleta atleta) {

        validarModalidade(atleta);

        validarBasquete(atleta);

        validarSalario(atleta);
    }

    private void validarModalidade(Atleta atleta) {

        String modalidade =
                atleta.getModalidade();


        if (modalidade == null ||
            modalidade.isBlank()) {

            throw new RuntimeException(
                "A modalidade é obrigatória."
            );
        }


        if (!modalidade.equalsIgnoreCase("Futebol")
                && !modalidade.equalsIgnoreCase("Basquete")
                && !modalidade.equalsIgnoreCase("Natação")
                && !modalidade.equalsIgnoreCase("Atletismo")) {

            throw new RuntimeException(
                "Modalidade inválida. " +
                "Escolha Futebol, Basquete, Natação ou Atletismo."
            );
        }


        // Padroniza a modalidade

        if (modalidade.equalsIgnoreCase("Futebol")) {

            atleta.setModalidade("Futebol");

        } else if (modalidade.equalsIgnoreCase("Basquete")) {

            atleta.setModalidade("Basquete");

        } else if (modalidade.equalsIgnoreCase("Natação")) {

            atleta.setModalidade("Natação");

        } else if (modalidade.equalsIgnoreCase("Atletismo")) {

            atleta.setModalidade("Atletismo");
        }
    }


    // ==============================
    // REGRA DO BASQUETE
    // ==============================

    private void validarBasquete(Atleta atleta) {

        if (atleta.getModalidade() != null
                && atleta.getModalidade()
                    .equalsIgnoreCase("Basquete")) {


            if (atleta.getIdade() == null) {

                throw new RuntimeException(
                    "A idade é obrigatória."
                );
            }


            if (atleta.getIdade() < 18) {

                throw new RuntimeException(
                    "Atletas de Basquete devem ter no mínimo 18 anos."
                );
            }
        }
    }

    private void validarSalario(Atleta atleta) {

        if (atleta.getSalarioMensal() == null) {

            throw new RuntimeException(
                "O salário mensal é obrigatório."
            );
        }


        if (atleta.getSalarioMensal().signum() < 0) {

            throw new RuntimeException(
                "O salário mensal não pode ser negativo."
            );
        }
    }
}