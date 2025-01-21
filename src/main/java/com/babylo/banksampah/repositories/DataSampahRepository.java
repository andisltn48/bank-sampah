package com.babylo.banksampah.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.babylo.banksampah.entities.DataSampah;

public interface DataSampahRepository extends JpaRepository<DataSampah, Long> , JpaSpecificationExecutor<DataSampah> {
    
}
