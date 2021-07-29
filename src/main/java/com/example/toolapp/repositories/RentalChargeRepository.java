package com.example.toolapp.repositories;

import com.example.toolapp.entities.RentalCharge;
import com.example.toolapp.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalChargeRepository extends JpaRepository<RentalCharge, Long> {

    RentalCharge findByToolTypeToolType(String toolType);

}