package com.ecoroute.ecoroute.Repositories;

import com.ecoroute.ecoroute.Model.Rota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RotaRepository extends JpaRepository<Rota,Integer> {
}
