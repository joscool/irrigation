package com.coolsoft.irrigation.land;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import java.time.LocalTime;

import com.coolsoft.irrigation.common.validation.EnumValidator;

@NoArgsConstructor
@Data
@ToString
@Entity
public class Schedule {

    public enum DayOfWeek {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESSDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    @Enumerated(EnumType.STRING)
    @EnumValidator(regexp="MONDAY|TUESDAY|WEDNESSDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY")
    private DayOfWeek dayOfWeek;

    private LocalTime startAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_id", nullable = false)
    @JsonIgnore
    private Land land;
}
