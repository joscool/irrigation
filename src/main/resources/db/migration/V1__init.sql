
    
    create table land (
        id integer not null AUTO_INCREMENT,
        description varchar(50),
        irrigation_duration integer not null,
        size integer not null,
        volume_of_water bigint not null,
        soil_type varchar(10),
        unit varchar(5),
        primary key (id)
    );

    
    create table schedule (
        id integer not null AUTO_INCREMENT,
        day_of_week varchar(10),
        start_at time not null,
        land_id integer,
        primary key (id)
    );

    
    alter table schedule 
       add constraint fk_land_id 
       foreign key (land_id) 
       references land(id);