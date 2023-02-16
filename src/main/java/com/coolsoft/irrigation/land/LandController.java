package com.coolsoft.irrigation.land;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.net.URI;

@RestController
@RequestMapping("api/v1/irrigation/land")
public class LandController {
    private final LandService _landService;

    public LandController(LandService landService) {
        _landService = landService;
    }

    @PostMapping
    public ResponseEntity<Land> postNewLand(final @Valid @RequestBody Land newLand) {
        _landService.addNewLand(newLand);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLand.getId())
                .toUri();
        return ResponseEntity.created(location).body(newLand);
    }

    @GetMapping
    public ResponseEntity<List<Land>> listLand(){
         final List<Land> land =  _landService.getAll();
         return ResponseEntity.ok().body(land);
    }

    @GetMapping("{id}")
    public ResponseEntity<Land> listLandById(@PathVariable("id") final int id){
         return ResponseEntity.of(_landService.getLandById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Land> deleteLandById(@PathVariable("id") final int id){
        try{
         _landService.deleteLandById(id);
         return ResponseEntity.noContent().build();
        }catch(EmptyResultDataAccessException ex){
                return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Land> updateLandById(@PathVariable("id") int id, @Valid @RequestBody final Land land){
        return ResponseEntity.of (_landService.updateLandInfo(id,land));
    }


    // Error handlers, could also use controller advice too
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());
        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key, val);
        });
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(HttpMessageNotReadableException ex) {
        final Map<String, String> map = new HashMap<>();
        String key = null;
        String error = null;
        if(ex.getMessage().contains("LocalTime")){
            key= "startAt";
            error= "invalid time format, valid format is HH:MM:SS e.g 16:00:00. Time is 24hrs format.";
        }else{
            // some dirty hack to give friendly error message
            key= ex.getLocalizedMessage().split("\\$")[1].split("`")[0];
            error= "should be one of:"+ex.getLocalizedMessage().split("class:")[1];
        }
       
        map.put(key,error);
        return ResponseEntity.badRequest().body(map);
    }
}
