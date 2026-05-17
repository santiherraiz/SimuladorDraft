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
            System.out.println("Iniciando carga matemática de campeones...");

            InputStream is = new ClassPathResource("champion.json").getInputStream();
            RiotChampionResponse response = objectMapper.readValue(is, RiotChampionResponse.class);

            List<Champion> championsToSave = new ArrayList<>();

            response.data().values().forEach(riotChamp -> {
                Champion champ = new Champion();
                champ.setId(riotChamp.id());
                champ.setName(riotChamp.name());
                champ.setRole(riotChamp.tags().isEmpty() ? "Desconocido" : riotChamp.tags().get(0));

                int attack = riotChamp.info().attack();
                int magic = riotChamp.info().magic();
                champ.setBaseAttackScore(attack);
                champ.setBaseMagicScore(magic);
                champ.setBaseDefenseScore(riotChamp.info().defense());
                champ.setDifficultyScore(riotChamp.info().difficulty());

                // Perfil de Daño
                if (attack >= magic + 4) {
                    champ.setDamageProfile("AD");
                } else if (magic >= attack + 4) {
                    champ.setDamageProfile("AP");
                } else {
                    champ.setDamageProfile("Híbrido");
                }

                // Rango y tipo de ataque
                double range = riotChamp.stats().getOrDefault("attackrange", 500.0);
                champ.setAttackRange((int) range);
                champ.setMelee(range < 300);

                // Perfil de aguante
                int defense = riotChamp.info().defense();
                if (defense >= 7) {
                    champ.setDurabilityProfile("Tanque");
                } else if (defense >= 4) {
                    champ.setDurabilityProfile("Luchador/Bruiser");
                } else {
                    champ.setDurabilityProfile("Papel/Squishy");
                }

                // Dificultad de ejecución
                int difficulty = riotChamp.info().difficulty();
                if (difficulty >= 7) {
                    champ.setExecutionDifficulty("Alta");
                } else if (difficulty >= 4) {
                    champ.setExecutionDifficulty("Media");
                } else {
                    champ.setExecutionDifficulty("Baja");
                }

                championsToSave.add(champ);
            });

            championRepository.saveAll(championsToSave);
            System.out.println("¡Carga completada! Campeones procesados: " + championsToSave.size());
        } else {
            System.out.println("Los campeones ya estaban en la base de datos.");
        }
    }
}