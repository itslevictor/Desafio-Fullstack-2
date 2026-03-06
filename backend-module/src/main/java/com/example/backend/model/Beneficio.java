package com.example.backend.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BENEFICIO")
public class Beneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    private String descricao;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    private Boolean ativo = true;

    @Version // Requisito para Bug EJB Part 2: Optimistic Locking
    private Long version;

    // Construtores, Getters e Setters
}