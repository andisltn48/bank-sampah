package com.babylo.banksampah.repositories;

import java.util.List;

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
