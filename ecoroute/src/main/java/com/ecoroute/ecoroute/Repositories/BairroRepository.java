package com.ecoroute.ecoroute.Repositories;

import com.ecoroute.ecoroute.Model.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BairroRepository extends JpaRepository<Bairro,Integer> {

    Optional<Bairro> findByNome(String nome);

    @Query("SELECT COUNT(b) FROM Bairro b")
    int contarTotalDeBairros();

}