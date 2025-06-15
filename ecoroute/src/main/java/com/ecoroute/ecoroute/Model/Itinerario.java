package com.ecoroute.ecoroute.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itinerarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Itinerario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario responsavel;

    @ManyToOne
    @JoinColumn(name = "caminhao_id",nullable = false)
    private Caminhao caminhao;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) //deleta a rota junto com o itinerario
    @JoinColumn(name = "rota_id", referencedColumnName = "id")
    private Rota rota;

    @Column(nullable = false)
    private String dataExecucao;

    @ManyToOne
    @JoinColumn(name = "residuo_id", nullable = false)
    private Residuo residuo;

    public Itinerario(Usuario responsavel, Caminhao caminhao, Rota rota, String dataExecucao, Residuo residuo) {
        this.responsavel = responsavel;
        this.caminhao = caminhao;
        this.rota = rota;
        this.dataExecucao = dataExecucao;
        this.residuo = residuo;
    }
}