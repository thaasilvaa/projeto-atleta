package com.api.senai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(
    name = "tb_atletas",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_nome_modalidade",
            columnNames = {"nome", "modalidade"}
        )
    }
)
public class Atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do atleta é obrigatório.")
    @Size(
        min = 2,
        max = 100,
        message = "O nome deve ter entre 2 e 100 caracteres."
    )
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A modalidade é obrigatória.")
    @Column(nullable = false, length = 20)
    private String modalidade;

    @NotNull(message = "A idade é obrigatória.")
    @Min(
        value = 1,
        message = "A idade deve ser maior que zero."
    )
    @Column(nullable = false)
    private Integer idade;

    @NotNull(message = "O salário mensal é obrigatório.")
    @DecimalMin(
        value = "0.00",
        inclusive = true,
        message = "O salário mensal não pode ser negativo."
    )
    @Column(
        name = "salario_mensal",
        nullable = false,
        precision = 10,
        scale = 2
    )
    private BigDecimal salarioMensal;


    // CONSTRUTOR VAZIO
    public Atleta() {
    }


    // GETTER E SETTER DO ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    // GETTER E SETTER DO NOME
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    // GETTER E SETTER DA MODALIDADE
    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }


    // GETTER E SETTER DA IDADE
    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }


    // GETTER E SETTER DO SALÁRIO
    public BigDecimal getSalarioMensal() {
        return salarioMensal;
    }

    public void setSalarioMensal(BigDecimal salarioMensal) {
        this.salarioMensal = salarioMensal;
    }
}
