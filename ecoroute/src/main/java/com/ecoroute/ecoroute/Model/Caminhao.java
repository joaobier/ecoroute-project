package com.ecoroute.ecoroute.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @Column(nullable = true)
    private String tipo_residuo;

    public Caminhao(String placa,int capacidade_maxima, String tipo_residuo){
        this.placa = placa;
        this.capacidade_maxima = capacidade_maxima;
        this.tipo_residuo = tipo_residuo;
    }
}