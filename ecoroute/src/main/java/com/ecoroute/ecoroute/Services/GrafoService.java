package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.RuasConexoes;
import com.ecoroute.ecoroute.Repositories.RuasConexoesRepository;
import com.ecoroute.ecoroute.Repositories.RuasConexoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrafoService {

    private final RuasConexoesRepository ruasConexoesRepository;

    int[][] grafo;

    @Autowired
    public GrafoService(RuasConexoesRepository ruasConexoesRepository) {
        this.ruasConexoesRepository = ruasConexoesRepository;
        try {
            this.grafo = pegaGrafo();
        } catch (Exception e) {
            this.grafo = new int[0][3]; // Grafo vazio em caso de falha
            System.err.println("Falha ao carregar o grafo: " + e.getMessage());
        }
    }

    private int[][] pegaGrafo(){
        List<RuasConexoes> ruasConexoes = ruasConexoesRepository.findAll();

        int tamanho = ruasConexoes.size();

        int[][] grafo = new int[tamanho][3];

        for(int i = 0; i < ruasConexoes.size(); i++){

            grafo[i][0] = ruasConexoes.get(i).getBairroOrigem().getId();
            grafo[i][1] = ruasConexoes.get(i).getBairroDestino().getId();
            double distancia = ruasConexoes.get(i).getDistancia();
            distancia = distancia*100;
            grafo[i][2] = (int )distancia; //ESTÁ EM METROS, CUIDADO

        }

        //TESTE PARA VER SE O GRAFO FOI CORRETAMENTE CRIADO
        desenhaGrafo(grafo);

        return grafo;

    }

    private void desenhaGrafo(int[][] grafo){
        for(int i = 0; i < grafo.length; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(""+grafo[i][j]+" ");
            }
            System.out.println();
        }
    }

}