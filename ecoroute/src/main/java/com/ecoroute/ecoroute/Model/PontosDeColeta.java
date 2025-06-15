package com.ecoroute.ecoroute.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pontos_coleta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PontosDeColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    @JoinColumn(name = "bairro_id", nullable = false)
    private Bairro bairro;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String horarioFuncionamento;

    @ManyToMany
    @JoinTable(
            name = "ponto_residuos",
            joinColumns = @JoinColumn(name = "ponto_id"),
            inverseJoinColumns = @JoinColumn(name = "residuo_id")
    )
    private List<Residuo> residuosAceitos = new ArrayList<>();

    public PontosDeColeta(Bairro bairro, String nome, Usuario usuario, String endereco, String residuosAceitaveis, String horarioFuncionamento, ArrayList<Residuo> residuos) {
        this.bairro = bairro;
        this.nome = nome;
        this.usuario = usuario;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.residuosAceitos = residuos;
    }

    public PontosDeColeta(Bairro bairro, String nome, Usuario usuario, String endereco, String horarioFuncionamento) {
        this.bairro = bairro;
        this.nome = nome;
        this.usuario = usuario;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
    }
}