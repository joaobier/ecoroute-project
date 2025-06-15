package com.ecoroute.ecoroute.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "rotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @ManyToMany
    @OrderColumn(name = "ordem")
    private List<Bairro> bairrosVisitados;

    @Column(nullable = false)
    private Double distancia;

    @OneToOne(mappedBy = "rota")
    private Itinerario itinerario;

    public Rota(List<Bairro> bairrosVisitados, Double distancia) {
        this.bairrosVisitados = bairrosVisitados;
        this.distancia = distancia;
    }
}