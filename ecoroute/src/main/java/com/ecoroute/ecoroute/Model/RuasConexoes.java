package com.ecoroute.ecoroute.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ruas_conexoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RuasConexoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @ManyToMany
    @JoinTable(
            name = "bairro_rua_conexao",
            joinColumns = @JoinColumn(name = "rua_conexao_id"),
            inverseJoinColumns = @JoinColumn(name = "bairro_id")
    )
    private Set<Bairro> bairros = new HashSet<>();

    @Column(nullable = false)
    private long distancia;

}