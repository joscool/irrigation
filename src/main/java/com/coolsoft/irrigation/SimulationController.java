package com.coolsoft.irrigation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.coolsoft.irrigation.simulator.ISensor;
import com.coolsoft.irrigation.land.IScheduleRepository;
import com.coolsoft.irrigation.land.Schedule;
import com.coolsoft.irrigation.land.Land;

import java.util.List;

@RestController
@RequestMapping("api/v1/irrigation/schedule")
public class SimulationController {

    private IScheduleRepository _scheduleRep;
    private ISensor _sensor;
    public SimulationController(IScheduleRepository scheduleRep, ISensor sensor) {
        _scheduleRep = scheduleRep;
        _sensor = sensor;
    }


    /***
     *  This endpoint will upload configured schedules to the mock sensor and this will force the sensor to irrigate
     *  at the scheduled time
     */
    @GetMapping
    public ResponseEntity<String> configureSensor(){
        List<Schedule>  schedules = _scheduleRep.findAll();
        for(Schedule schedule:schedules){
            final Land land = schedule.getLand();
            _sensor.schedule(land.getId(), schedule.getStartAt(), land.getVolumeOfWater(), land.getIrrigationDuration(), schedule.getDayOfWeek());
        }

        return ResponseEntity.ok().body("Irrigation shedule uploaded to sensor successfully");
    }
    
}
