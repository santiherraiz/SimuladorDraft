package com.santiago.SimuladorDraft.dto;

import java.util.List;
import java.util.Map;

public record RiotChampionDTO(
        String id,
        String name,
        List<String> tags,
        RiotChampionInfo info,
        Map<String, Double> stats) {
}