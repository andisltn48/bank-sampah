package com.babylo.banksampah.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;



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

    @GetMapping()
    public ResponseEntity<ApiResponse<List<DataSampah>>> getAllDataSampah() {
        List<DataSampah> dataSampah = dataSampahService.getAllDataSampah();
        return new ResponseEntity<>(new ApiResponse<>(dataSampah), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DataSampah>> getDetailSampah(@PathVariable("id") Long id) {
        DataSampah dataSampah = dataSampahService.getDetailSampah(id);
        return new ResponseEntity<>(new ApiResponse<>(dataSampah), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<DataSampah>> updateDataSampah(@PathVariable("id") Long id, @Valid @RequestBody DataSampahDto request) {
        DataSampah dataSampah = dataSampahService.updateDataSampah(id, request);
        return new ResponseEntity<>(new ApiResponse<>(dataSampah), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDataSampah(@PathVariable("id") Long id) {
        dataSampahService.deleteDataSampah(id);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.NO_CONTENT);
    }
    
    
}
