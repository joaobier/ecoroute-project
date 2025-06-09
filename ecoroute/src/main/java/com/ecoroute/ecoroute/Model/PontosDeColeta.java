package com.ecoroute.ecoroute.Model;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Bairro bairro;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Usuario responsavel;

    //ISSO DEVERIA ESTAR NO USUARIO -> CONVERSAR COM O GUSTAVO
    @Column(nullable = false)
    private String telefone_responsavel;

    //ISSO DEVERIA ESTAR NO USUARIO -> CONVERSAR COM O GUSTAVO
    @Column(nullable = false)
    private String email_responsavel;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String horarioFuncionamento;

    //ISSO PRECISA SER REFORMULADO, CONVERSAR COM O GUSTAVO
    @Column(nullable = false)
    private String residuosAceitaveis;


}
