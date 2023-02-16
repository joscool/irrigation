package com.coolsoft.irrigation.land;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILandRepository extends JpaRepository<Land, Integer>{

}
