package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Auditoria;
import com.ecoroute.ecoroute.Repositories.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    @Autowired
    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public Optional<Auditoria> buscarPorId(int id){
        return auditoriaRepository.findById(id);
    }

    public List<Auditoria> listarTodos() {
        return auditoriaRepository.findAll();
    }

    public Auditoria salvar(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }

    public Auditoria editar(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }

    public void deletar(int id) {
        auditoriaRepository.deleteById(id);
    }

}