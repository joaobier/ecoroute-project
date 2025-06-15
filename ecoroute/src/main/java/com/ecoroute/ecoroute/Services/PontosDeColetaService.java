package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.PontosDeColeta;
import com.ecoroute.ecoroute.Model.Residuo;
import com.ecoroute.ecoroute.Repositories.PontosDeColetaRepository;
import com.ecoroute.ecoroute.Repositories.ResiduoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PontosDeColetaService {

    private final PontosDeColetaRepository pontosDeColetaRepository;
    private final ResiduoRepository residuoRepository;

    @Autowired
    public PontosDeColetaService(PontosDeColetaRepository pontosDeColetaRepository, ResiduoRepository residuoRepository) {
        this.pontosDeColetaRepository = pontosDeColetaRepository;
        this.residuoRepository = residuoRepository;
    }

    public List<PontosDeColeta> listarTodos() {
        return pontosDeColetaRepository.findAll();
    }

    public Optional<PontosDeColeta> buscarPorId(int id){
        return pontosDeColetaRepository.findById(id);
    }

    public PontosDeColeta salvar(PontosDeColeta pontosDeColeta) {
        return pontosDeColetaRepository.save(pontosDeColeta);
    }

    public PontosDeColeta editar(PontosDeColeta pontosDeColeta) {
        return pontosDeColetaRepository.save(pontosDeColeta);
    }

    public void deletar(int id) {
        pontosDeColetaRepository.deleteById(id);
    }

    public PontosDeColeta adicionarResiduoAoPonto(int pontoId, int residuoId) {
        PontosDeColeta ponto = pontosDeColetaRepository.findById(pontoId)
                .orElseThrow(() -> new RuntimeException("Ponto de coleta não encontrado"));

        Residuo residuo = residuoRepository.findById(residuoId)
                .orElseThrow(() -> new RuntimeException("Resíduo não encontrado"));

        if (ponto.getResiduosAceitos().contains(residuo)) {
            throw new RuntimeException("Este resíduo já foi adicionado a este ponto");
        }

        ponto.getResiduosAceitos().add(residuo);
        return pontosDeColetaRepository.save(ponto);
    }

}