package com.ecoroute.ecoroute.Repositories;

import com.ecoroute.ecoroute.Model.PontosDeColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontosDeColetaRepository extends JpaRepository<PontosDeColeta,Integer> {
}
