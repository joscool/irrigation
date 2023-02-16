package com.coolsoft.irrigation.simulator;

import java.time.LocalTime;
import com.coolsoft.irrigation.land.Schedule.DayOfWeek;

public interface ISensor {
    public void schedule(int landId, LocalTime startAt, long waterVolume, int duration, DayOfWeek day);
}
