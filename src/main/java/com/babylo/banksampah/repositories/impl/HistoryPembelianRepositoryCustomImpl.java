package com.babylo.banksampah.repositories.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.babylo.banksampah.entities.DataSampah;
import com.babylo.banksampah.entities.HistoryPembelian;
import com.babylo.banksampah.repositories.HistoryPembelianRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class HistoryPembelianRepositoryCustomImpl implements HistoryPembelianRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    public List<HistoryPembelian> findAllHistoryPembelian(
        String searchTerm, 
        String orderByColumn, 
        String orderDirection,
        int limit, 
        int offset
    ) {
        // Sanitize the input to avoid SQL injection
        String validOrderByColumn = "id"; // Default column
        if (orderByColumn != null && !orderByColumn.isEmpty()) {
            validOrderByColumn = orderByColumn;
        }

        String validOrderDirection = "ASC"; // Default direction
        if ("DESC".equals(orderDirection)) {
            validOrderDirection = "DESC";
        }

        // Build the dynamic query
        String queryString = "SELECT * FROM history_pembelian history "+
        "RIGHT JOIN list_history_pembelian list ON history.id = list.id_history "+
        "RIGHT JOIN data_sampah sampah ON list.id_sampah = sampah.id ";
        if (searchTerm != null && !searchTerm.isEmpty()) {
            queryString += "WHERE (sampah.nama_sampah LIKE ? OR sampah.unit LIKE ?) ";
        }
        queryString += "ORDER BY " + validOrderByColumn + " " + validOrderDirection + " "
                + "LIMIT "+limit+" OFFSET "+offset+" ";
        
        // Create the query
        Query query = entityManager.createNativeQuery(queryString, DataSampah.class);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            
            query.setParameter(1, "%" + searchTerm + "%"); // Second parameter for LIKE
            query.setParameter(2, "%" + searchTerm + "%"); // Third parameter for LIKE
        }

        // Execute the query
        return query.getResultList();
    }
}
