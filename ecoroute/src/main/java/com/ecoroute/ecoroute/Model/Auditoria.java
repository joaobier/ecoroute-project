package com.ecoroute.ecoroute.Model;

import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "auditoria")
@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    //vai ser só uma data, corrigir o banco de dados
    @Column(nullable = false)
    private String dataOcorrencia;

    @Column(nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String operacao;

    @Column(nullable = false)
    private String tabela;

    @Column(nullable = false)
    private String descricaoOcorrencia;

    public Auditoria(int id, Usuario usuario, String operacao, String tabela, String descricaoOcorrencia) {
        this.id = id;
        this.usuario = usuario;
        this.operacao = operacao;
        this.tabela = tabela;
        this.descricaoOcorrencia = descricaoOcorrencia;
        Date momentoExecucao = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.dataOcorrencia = formatter.format(momentoExecucao);
    }

    public Auditoria(){
        Date momentoExecucao = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.dataOcorrencia = formatter.format(momentoExecucao);
    }

}
