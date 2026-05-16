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

    private int waveclearScore;
    private boolean hasHardCc;
    private String damageProfile;
    private String powerSpike;
}