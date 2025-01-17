package com.babylo.banksampah.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.babylo.banksampah.entities.ListHistorySampah;

public interface ListHistorySampahRepository extends JpaRepository<ListHistorySampah, Long>{
    List<ListHistorySampah> findAllByIdHistoryAndType(Long idHistory, String type);
}
