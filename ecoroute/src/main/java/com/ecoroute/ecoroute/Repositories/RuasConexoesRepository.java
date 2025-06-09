package com.ecoroute.ecoroute.Repositories;

import com.ecoroute.ecoroute.Model.RuasConexoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuasConexoesRepository extends JpaRepository<RuasConexoes,Integer> {
}
