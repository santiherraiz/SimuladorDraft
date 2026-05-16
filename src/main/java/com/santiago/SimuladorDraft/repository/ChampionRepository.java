package com.santiago.SimuladorDraft.repository;

import com.santiago.SimuladorDraft.model.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, String> {
}