package com.coolsoft.irrigation.simulator;

import java.time.LocalTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.coolsoft.irrigation.land.Schedule.DayOfWeek;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.util.List;
import java.util.ArrayList;

/**
 * This mock sensor operates at a fixed shedule. It runs at interval of 10 secs. It does not use use the schedules for the land as that 
 * might be difficult to observe.
 * The schedules for the land would ideally be scheduled dynamically, but that is not implemented here.
 */

@Log
@Component
public class MockSensor implements ISensor {

    @Getter
    @ToString
    class Land {
        private int _landId;
        private LocalTime _startAt;
        private long _waterVolume;
        private int _duration;
        private DayOfWeek _day;

        public Land(final int landId, final LocalTime startAt, final long waterVolume, final int duration, final DayOfWeek day) {
            _landId = landId;
            _startAt = startAt;
            _waterVolume = waterVolume;
            _duration = duration;
            _day = day;
        }
    }

    private List<Land> lands = new ArrayList<>();

    public void schedule(final int landId, final LocalTime startAt, final long waterVolume, final int duration, final DayOfWeek day) {
        final Land land = new Land(landId, startAt, waterVolume, duration, day);
        lands.add(land);
        log.info(">>> Registered : " + land);
    }

    @Scheduled(fixedDelay=10000)
    public void irrigate() {
        try {
            if (lands.size() > 0) {
                log.info(String.format("----- Beginning irrigation for %d land(s) -----", lands.size()));
                for (Land land : lands) {
                    log.info(String.format("Irrigating Land %d for %d min(s)", land.get_landId(), land.get_duration())); // forgive lombock
                    Thread.sleep(2000); // pause for 3 secs
                }
            }
        } catch (Exception ex) {
            
        }
    }
}
