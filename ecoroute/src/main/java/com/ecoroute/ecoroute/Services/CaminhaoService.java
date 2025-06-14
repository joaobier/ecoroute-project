package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Caminhao;
import com.ecoroute.ecoroute.Repositories.CaminhaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaminhaoService {

    private final CaminhaoRepository caminhaoRepository;

    @Autowired
    public CaminhaoService(CaminhaoRepository caminhaoRepository){
        this.caminhaoRepository = caminhaoRepository;
    }

    public List<Caminhao> listarCaminhoes(){
        return caminhaoRepository.findAll();
    }

    public Caminhao salvar(Caminhao caminhao){
        return caminhaoRepository.save(caminhao);
    }

    public Optional<Caminhao> buscarPorId(int id){
        return caminhaoRepository.findById(id);
    }

    public Optional<Caminhao> buscarPorPlaca(String placa){
        return caminhaoRepository.findByPlaca(placa);
    }

    public Caminhao editar(Caminhao caminhao){ return caminhaoRepository.save(caminhao);}

    public void deletar(int id){
        caminhaoRepository.deleteById(id);
    }

}
