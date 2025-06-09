package com.ecoroute.ecoroute.Model;

import jakarta.persistence.*;
import lombok.*;

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

}