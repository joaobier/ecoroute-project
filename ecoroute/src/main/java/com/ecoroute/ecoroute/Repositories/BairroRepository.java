package com.ecoroute.ecoroute.Repositories;

import com.ecoroute.ecoroute.Model.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BairroRepository extends JpaRepository<Bairro,Integer> {
}
