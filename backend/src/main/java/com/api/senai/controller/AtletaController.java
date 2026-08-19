package com.api.senai.controller;

import com.api.senai.entity.Atleta;
import com.api.senai.service.AtletaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atletas")
@CrossOrigin(origins = "*")
public class AtletaController {

    private final AtletaService atletaService;

    public AtletaController(AtletaService atletaService) {
        this.atletaService = atletaService;
    }
    

    @GetMapping
    public ResponseEntity<List<Atleta>> listarTodos() {

        return ResponseEntity.ok(
            atletaService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atleta> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            atletaService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Atleta> cadastrar(
            @Valid @RequestBody Atleta atleta) {

        Atleta novoAtleta =
                atletaService.cadastrar(atleta);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoAtleta);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Atleta> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Atleta atleta) {

        Atleta atletaAtualizado =
                atletaService.atualizar(
                    id,
                    atleta
                );

        return ResponseEntity.ok(
            atletaAtualizado
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        atletaService.excluir(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
