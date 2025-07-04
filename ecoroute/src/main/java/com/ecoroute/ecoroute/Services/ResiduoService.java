package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Residuo;
import com.ecoroute.ecoroute.Repositories.ResiduoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResiduoService {

    private final ResiduoRepository residuoRepository;

    @Autowired
    public ResiduoService(ResiduoRepository residuoRepository){this.residuoRepository = residuoRepository;}

    public List<Residuo> listarTodos(){return residuoRepository.findAll();}

    public Optional<Residuo> buscarPorId(int id){return residuoRepository.findById(id);}

    public Residuo salvar(Residuo residuo){return residuoRepository.save(residuo);}

    public Residuo editar(Residuo residuo){return residuoRepository.save(residuo);}

    public boolean deletar(int id) {
        if (residuoRepository.existsById(id)) {
            residuoRepository.deleteById(id);
            return true;// Deletou com sucesso
        } else {
            return false;// Não existia
        }
    }

}