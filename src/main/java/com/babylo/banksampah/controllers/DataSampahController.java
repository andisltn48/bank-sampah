package com.babylo.banksampah.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babylo.banksampah.dto.DataSampahDto;
import com.babylo.banksampah.entities.DataSampah;
import com.babylo.banksampah.responses.ApiResponse;
import com.babylo.banksampah.services.DataSampahService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/data-sampah")
public class DataSampahController {

    @Autowired
    private DataSampahService dataSampahService;

    @PostMapping()
    public ResponseEntity<ApiResponse<DataSampah>> addSampah(@Valid @RequestBody DataSampahDto request) {
        DataSampah dataSampah = dataSampahService.addDataSampah(request);
        return new ResponseEntity<>(new ApiResponse<>(dataSampah), HttpStatus.OK);
    }
    
}
