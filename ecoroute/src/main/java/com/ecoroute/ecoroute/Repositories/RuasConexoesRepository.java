package com.ecoroute.ecoroute.Repositories;

import com.ecoroute.ecoroute.Model.RuasConexoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuasConexoesRepository extends JpaRepository<RuasConexoes,Integer> {
    boolean existsByBairroOrigemIdAndBairroDestinoId(int origemId, int destinoId);
    void deleteByBairroOrigemIdAndBairroDestinoId(int origemId, int destinoId);
    Optional<RuasConexoes> findByBairroOrigemIdAndBairroDestinoId(int origemId, int destinoId);
}