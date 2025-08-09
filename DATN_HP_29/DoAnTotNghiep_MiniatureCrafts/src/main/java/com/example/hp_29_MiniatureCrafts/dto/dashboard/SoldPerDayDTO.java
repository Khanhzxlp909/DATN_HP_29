package com.example.hp_29_MiniatureCrafts.dto.dashboard;

import java.time.LocalDate;

public record SoldPerDayDTO(LocalDate day, Long quantity) {}