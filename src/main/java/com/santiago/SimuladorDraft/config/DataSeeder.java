package com.santiago.SimuladorDraft.config;

import com.santiago.SimuladorDraft.dto.RiotChampionResponse;
import com.santiago.SimuladorDraft.model.Champion;
import com.santiago.SimuladorDraft.repository.ChampionRepository;

import tools.jackson.databind.ObjectMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ChampionRepository championRepository;
    private final ObjectMapper objectMapper;

    public DataSeeder(ChampionRepository championRepository, ObjectMapper objectMapper) {
        this.championRepository = championRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (championRepository.count() == 0) {
            System.out.println("Iniciando carga de campeones...");

            InputStream is = new ClassPathResource("champion.json").getInputStream();
            RiotChampionResponse response = objectMapper.readValue(is, RiotChampionResponse.class);

            List<Champion> championsToSave = new ArrayList<>();

            response.data().values().forEach(riotChamp -> {
                Champion champ = new Champion();
                champ.setId(riotChamp.id());
                champ.setName(riotChamp.name());
                champ.setRole(riotChamp.tags().isEmpty() ? "Desconocido" : riotChamp.tags().get(0));
                champ.setBaseAttackScore(riotChamp.info().attack());
                champ.setBaseMagicScore(riotChamp.info().magic());

                championsToSave.add(champ);
            });

            championRepository.saveAll(championsToSave);
            System.out.println("¡Carga completada! Campeones guardados: " + championsToSave.size());
        } else {
            System.out.println("Los campeones ya estaban en la base de datos.");
        }
    }
}