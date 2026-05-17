package com.santiago.SimuladorDraft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Champion {
    @Id
    private String id;
    private String name;
    private String role;

    private int baseAttackScore;
    private int baseMagicScore;
    private int baseDefenseScore;
    private int attackRange;
    private int difficultyScore;

    private boolean isMelee;
    private String damageProfile;
    private String durabilityProfile;
    private String executionDifficulty;

    private int waveclearScore;
    private boolean hasHardCc;
    private String powerSpike;
}