package com.ecoroute.ecoroute.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "caminhoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Caminhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false)
    private String placa;

    @Column(nullable = true)
    private int capacidade_maxima;

    @ManyToMany
    @JoinTable(
            name = "caminhao_residuos",
            joinColumns = @JoinColumn(name = "caminhao_id"),
            inverseJoinColumns = @JoinColumn(name = "residuo_id")
    )
    private List<Residuo> residuosTransportados = new ArrayList<>();

    public Caminhao(String placa,int capacidade_maxima){
        this.placa = placa;
        this.capacidade_maxima = capacidade_maxima;
    }

    public Caminhao(String placa, int capacidade_maxima, List<Residuo> residuosTransportados) {
        this.placa = placa;
        this.capacidade_maxima = capacidade_maxima;
        this.residuosTransportados = residuosTransportados;
    }
}