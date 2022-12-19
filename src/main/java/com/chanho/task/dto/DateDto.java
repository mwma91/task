package com.chanho.task.dto;

import java.time.LocalDate;

public class DateDto {
    private LocalDate from;
    private LocalDate until;

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getUntil() {
        return until;
    }

    public void setUntil(LocalDate until) {
        this.until = until;
    }
}
