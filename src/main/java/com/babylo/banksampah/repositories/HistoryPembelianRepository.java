package com.babylo.banksampah.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.babylo.banksampah.entities.HistoryPembelian;

public interface HistoryPembelianRepository extends JpaRepository<HistoryPembelian, Long>, HistoryPembelianRepositoryCustom {
    @Query(value = "SELECT * FROM history_pembelian history "+
        "RIGHT JOIN list_history_pembelian list ON history.id = list.id_history "+
        "RIGHT JOIN data_sampah sampah ON list.id_sampah = sampah.id "+
        "WHERE (:search IS NULL OR sampah.nama_sampah LIKE %:search% OR sampah.unit LIKE %:search%)", 
    nativeQuery = true)
    long countHistoryPembelian(@Param("search") String search);
}
