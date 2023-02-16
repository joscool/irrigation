package com.coolsoft.irrigation.land;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class LandService {

    private final ILandRepository _repository;

    public LandService(final ILandRepository repository) {
        _repository = repository;
    }

    public void addNewLand(final Land land) {
        for(Schedule schedule: land.getSchedules())
        {
            schedule.setLand(land);
        }
        _repository.save(land);
    }

    public List<Land> getAll(){
       return _repository.findAll();
    }

    public Optional<Land >getLandById(final int id){
        return _repository.findById(id);
    }

    public void deleteLandById(final int id){
        _repository.deleteById(id);
    }

    public Optional<Land>  updateLandInfo(final int id,final Land updatedLand){
       final Optional<Land> foundLand =  _repository.findById(id);
       if(foundLand.isPresent()){
            final Land landToUpdate = foundLand.get();
            landToUpdate.setIrrigationDuration(updatedLand.getIrrigationDuration());
            landToUpdate.setSize(updatedLand.getSize());
            landToUpdate.setSoilType(updatedLand.getSoilType());
            landToUpdate.setUnit(updatedLand.getUnit());
            landToUpdate.setDescription(updatedLand.getDescription());
            landToUpdate.setVolumeOfWater(updatedLand.getVolumeOfWater());
            _repository.save(landToUpdate);
            return Optional.of(landToUpdate);
       }
       return foundLand;
    }
}
