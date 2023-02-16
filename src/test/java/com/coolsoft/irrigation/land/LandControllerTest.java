package com.coolsoft.irrigation.land;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.coolsoft.irrigation.land.Schedule.DayOfWeek;

@WebMvcTest(LandController.class)
public class LandControllerTest {

    @MockBean
    private LandService _landService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    final String basePath =  "/api/v1/irrigation/land";

    @Test
    public void shouldReturnEmptyLandList() throws Exception{
        List<Land> emptyLand = new ArrayList<>();

        when(_landService.getAll()).thenReturn(emptyLand);
        
        mockMvc.perform(get(basePath))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(emptyLand.size()))
            .andDo(print());
            
    }

    @Test
    public void shouldReturnNoneEmptyLandList() throws Exception{
        Land l1 = new Land();
        l1.setId(1);
        l1.setIrrigationDuration(20);
        l1.setDescription("Land 1");
        l1.setSize(40000);
        l1.setSoilType(Land.SoilType.CLAY);
        l1.setUnit(Land.Unit.HA);

        Land l2= new Land();
        l2.setId(2);
        l2.setIrrigationDuration(50);
        l2.setDescription("Land 2");
        l2.setSize(470000);
        l2.setSoilType(Land.SoilType.LOAMY);
        l2.setUnit(Land.Unit.SQM);
        List<Land> land = List.of(
            l1,l2
        );

        when(_landService.getAll()).thenReturn(land);
        
        mockMvc.perform(get(basePath))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(land.size()))
            .andDo(print());
            
    }

    @Test
    public void shouldReturnNotFoundLand() throws Exception{

        final int id = 1;
        when(_landService.getLandById(1)).thenReturn(Optional.empty());
        
        mockMvc.perform(get(basePath+"/{id}",id))
            .andExpect(status().isNotFound())
            .andDo(print());
            
    }

    @Test
    public void shouldReturnLandGivenAValidId() throws Exception{
        final Land land= createTestLand(false);

        when(_landService.getLandById(1)).thenReturn(Optional.of(land));
        
        mockMvc.perform(get(basePath+"/{id}",land.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(land.getId()))
            .andExpect(jsonPath("$.soilType").value(land.getSoilType().name()))
            .andExpect(jsonPath("$.description").value(land.getDescription()))
            .andExpect(jsonPath("$.irrigationDuration").value(land.getIrrigationDuration()))
            .andExpect(jsonPath("$.size").value(land.getSize()))
            .andExpect(jsonPath("$.unit").value(land.getUnit().name()))
            .andDo(print());
            
    }

    @Test
    public void shouldCreateLandWithoutSchedules() throws Exception {
      final Land land= createTestLand(false);
  
      mockMvc.perform(post(basePath).contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(land)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id").value(land.getId()))
          .andExpect(jsonPath("$.soilType").value(land.getSoilType().name()))
          .andExpect(jsonPath("$.description").value(land.getDescription()))
          .andExpect(jsonPath("$.irrigationDuration").value(land.getIrrigationDuration()))
          .andExpect(jsonPath("$.size").value(land.getSize()))
          .andExpect(jsonPath("$.unit").value(land.getUnit().name()))
          .andExpect(jsonPath("$.schedules.size()").value(land.getSchedules().size()))
          .andDo(print());
    }

    @Test
    public void shouldCreateLandWithSchedules() throws Exception {
      final Land land= createTestLand(true);
  
      mockMvc.perform(post(basePath).contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(land)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id").value(land.getId()))
          .andExpect(jsonPath("$.soilType").value(land.getSoilType().name()))
          .andExpect(jsonPath("$.description").value(land.getDescription()))
          .andExpect(jsonPath("$.irrigationDuration").value(land.getIrrigationDuration()))
          .andExpect(jsonPath("$.size").value(land.getSize()))
          .andExpect(jsonPath("$.unit").value(land.getUnit().name()))
          .andExpect(jsonPath("$.schedules.size()").value(land.getSchedules().size()))
          .andDo(print());
    }

    @Test
  public void shouldUpdateLand() throws Exception {
    final int id = 1;
    final Land updatedLand = createTestLand(false);
    updatedLand.setDescription("Land 3");

    when(_landService.updateLandInfo(anyInt(),any(Land.class))).thenReturn(Optional.of(updatedLand));

    mockMvc.perform(put(basePath+"/{id}", id).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedLand)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(updatedLand.getId()))
        .andExpect(jsonPath("$.soilType").value(updatedLand.getSoilType().name()))
        .andExpect(jsonPath("$.description").value(updatedLand.getDescription()))
        .andExpect(jsonPath("$.irrigationDuration").value(updatedLand.getIrrigationDuration()))
        .andExpect(jsonPath("$.size").value(updatedLand.getSize()))
        .andExpect(jsonPath("$.unit").value(updatedLand.getUnit().name()))
        .andExpect(jsonPath("$.schedules.size()").value(updatedLand.getSchedules().size()))
        .andDo(print());
  }

    @Test
    public void shouldDeleteTutorial() throws Exception {
      final int id = 1;
  
      doNothing().when(_landService).deleteLandById(anyInt());
      mockMvc.perform(delete(basePath+"/{id}",id))
           .andExpect(status().isNoContent())
           .andDo(print());
    }

    private Land createTestLand(boolean shouldCreateSchedule){
        Land land = new Land();
        land.setId(1);
        land.setIrrigationDuration(20);
        land.setDescription("Land 1");
        land.setSize(40000);
        land.setSoilType(Land.SoilType.CLAY);
        land.setUnit(Land.Unit.HA);
        if(shouldCreateSchedule){
            final Schedule schedule = new Schedule();
            schedule.setDayOfWeek(DayOfWeek.FRIDAY);
            schedule.setStartAt(LocalTime.NOON);
            schedule.setId(1);
            land.addSchedule(schedule);
        }
        return land;
    }


}
