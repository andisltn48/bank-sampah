package com.babylo.banksampah.repositories;

import java.util.List;

import com.babylo.banksampah.entities.HistoryPembelian;

public interface HistoryPembelianRepositoryCustom {

    List<HistoryPembelian> findAllHistoryPembelian(
        String searchTerm, 
        String orderByColumn, 
        String orderDirection,
        int limit, 
        int offset
    );
}
