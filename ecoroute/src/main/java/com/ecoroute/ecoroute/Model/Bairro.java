package com.ecoroute.ecoroute.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bairros")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Bairro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "bairro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PontosDeColeta> pontosDeColeta = new ArrayList<>();

    public Bairro(String nome) {
        this.nome = nome;
    }

    public Bairro(String nome, List<PontosDeColeta> pontosDeColeta) {
        this.nome = nome;
        this.pontosDeColeta = pontosDeColeta;
    }
}