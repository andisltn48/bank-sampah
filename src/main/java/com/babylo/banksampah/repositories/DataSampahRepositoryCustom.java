package com.babylo.banksampah.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.babylo.banksampah.entities.DataSampah;

public interface DataSampahRepositoryCustom {

    List<DataSampah> findAllDataSampah(
        String searchTerm, 
        String orderByColumn, 
        String orderDirection,
        int limit, 
        int offset
    );
}
