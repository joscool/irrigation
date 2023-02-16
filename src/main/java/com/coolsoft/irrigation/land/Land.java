package com.coolsoft.irrigation.land;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import com.coolsoft.irrigation.common.validation.EnumValidator;

@NoArgsConstructor
@Data
@ToString
@Entity
public class Land {

    enum SoilType {
        SANDY,
        CLAY,
        SILTY,
        LOAMY
    }

    enum Unit {
        HA, // hecters
        SQM // square meter
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value=1,message = "land size must be greater than or equal to 1")
    private int size;

    @Min(value=1,message = "water volume must be greater than or equal to 1")
    private long volumeOfWater;

    @NotBlank(message = "description is mandatory")
    private String description;

   
    @Enumerated(EnumType.STRING)
    @EnumValidator(regexp="SANDY|CLAY|SILTY|LOAMY")
    private SoilType soilType;

    @Enumerated(EnumType.STRING)
    @EnumValidator(regexp="HA|SQM")
    private Unit unit;
    
    @Min(value=1,message = "irrigation duration must be equal to or greater than 1 minute")
    private int irrigationDuration; // time to complete irrigation in minutes

    @OneToMany(
        mappedBy = "land",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<Schedule> schedules = new ArrayList<>();

    public void addSchedule(Schedule schedule){
        schedules.add(schedule);
    }
}
