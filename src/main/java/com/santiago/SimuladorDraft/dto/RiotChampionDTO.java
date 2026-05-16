package com.santiago.SimuladorDraft.dto;

import java.util.List;

public record RiotChampionDTO(
    String id,
    String name,
    List<String> tags,
    RiotChampionInfo info
) {}