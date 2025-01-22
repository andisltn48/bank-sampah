package com.babylo.banksampah.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.babylo.banksampah.entities.DataSampah;

public interface DataSampahRepository extends JpaRepository<DataSampah, Long>, DataSampahRepositoryCustom{

    @Query(value = "SELECT COUNT(*) FROM data_sampah sampah " +
    "WHERE (:search IS NULL OR sampah.nama_sampah LIKE %:search% OR sampah.unit LIKE %:search%)", 
    nativeQuery = true)
    long countDataSampah(@Param("search") String search);
}
