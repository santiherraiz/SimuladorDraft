package com.santiago.SimuladorDraft.dto;

import java.util.Map;

public record RiotChampionResponse(
        String type,
        String format,
        String version,
        Map<String, RiotChampionDTO> data) {
}