package com.ecoroute.ecoroute.Repositories;

import com.ecoroute.ecoroute.Model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria,Integer> {
}
