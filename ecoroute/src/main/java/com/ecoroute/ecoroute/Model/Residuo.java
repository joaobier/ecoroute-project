package com.ecoroute.ecoroute.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "residuos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Residuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String tipo;

    @ManyToMany(mappedBy = "residuosAceitos")
    @JsonIgnore
    private List<PontosDeColeta> pontosDeColeta = new ArrayList<>();

    public Residuo(String tipo) {
        this.tipo = tipo;
    }
}