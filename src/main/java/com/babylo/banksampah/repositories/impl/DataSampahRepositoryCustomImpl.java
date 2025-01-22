package com.babylo.banksampah.repositories.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.babylo.banksampah.entities.DataSampah;
import com.babylo.banksampah.repositories.DataSampahRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class DataSampahRepositoryCustomImpl implements DataSampahRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    public List<DataSampah> findAllDataSampah(
        String searchTerm, 
        String orderByColumn, 
        String orderDirection,
        int limit, 
        int offset) {
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
        String queryString = "SELECT * FROM data_sampah sampah ";
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

        // query.setParameter(1, searchTerm); // First parameter for WHERE clause
        // query.setParameter(4, limit); // Set LIMIT
        // query.setParameter(5, offset); // Set OFFSET

        // Execute the query
        return query.getResultList();
    }
}
