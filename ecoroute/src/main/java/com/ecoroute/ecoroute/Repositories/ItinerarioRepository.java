package com.ecoroute.ecoroute.Repositories;

import com.ecoroute.ecoroute.Model.Itinerario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItinerarioRepository extends JpaRepository<Itinerario,Integer> {
}
