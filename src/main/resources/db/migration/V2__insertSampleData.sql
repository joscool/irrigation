insert into 
  land ( 
    description, 
    irrigation_duration, 
    size, 
    volume_of_water, 
    soil_type, 
    unit
  )
values
  (
    'Yam plantation', 
    60, 
    4872, 
    4567890, 
    'LOAMY', 
    'HA'
  ),
  (
    'Cassava plantation', 
    60, 
    872, 
    67890, 
    'SANDY', 
    'HA'
  ),
  (
    'Rice plantation', 
    20, 
    972, 
    7890, 
    'CLAY', 
    'HA'
  );

    insert into 
    schedule (
      day_of_week, 
      start_at, 
      land_id
    )
  values
    ( 
      'SUNDAY', 
      '16:00:00', 
      '1'
    ),
    ( 
      'MONDAY', 
      '16:00:00', 
      '2'
    );