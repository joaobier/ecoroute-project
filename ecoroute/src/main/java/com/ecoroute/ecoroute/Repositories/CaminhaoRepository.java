package com.ecoroute.ecoroute.Repositories;

import com.ecoroute.ecoroute.Model.Caminhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao,Integer> {

    Optional<Caminhao> findByPlaca(String placa);

}
