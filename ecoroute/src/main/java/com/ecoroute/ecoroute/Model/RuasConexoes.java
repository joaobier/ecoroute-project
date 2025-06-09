package com.ecoroute.ecoroute.Model;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Bairro bairroOrigem;

    @Column(nullable = false)
    private Bairro bairroDestino;

    @Column(nullable = false)
    private long distancia;

}