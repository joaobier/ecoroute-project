package com.ecoroute.ecoroute.Model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Rota rota;

    @Column(nullable = false)
    private String dataExecucao;

}
