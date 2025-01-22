package com.babylo.banksampah.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.babylo.banksampah.dto.DataSampahDto;
import com.babylo.banksampah.dto.HistorySampahDto;
import com.babylo.banksampah.entities.DataSampah;
import com.babylo.banksampah.entities.HistoryPembelian;
import com.babylo.banksampah.entities.HistoryPenjualan;
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllDataSampah(
        @RequestParam(defaultValue = "id", name = "sortBy") String sortBy,
        @RequestParam(defaultValue = "ASC", name = "sortDirection") String sortDirection,
        @RequestParam(defaultValue = "1", name = "page") int page,
        @RequestParam(defaultValue = "10", name = "size") int size,
        @RequestParam(required = false, name = "search") String search
    ) {
        Map<String, Object> dataSampah = dataSampahService.getAllDataSampah(
            sortBy,
            sortDirection,
            page,
            size,
            search
        );
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
    public ResponseEntity<Void> deleteDataSampah(@PathVariable("id") Long id) {
        dataSampahService.deleteDataSampah(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/pembelian")
    public ResponseEntity<Void> pembelianSampah(@Valid @RequestBody HistorySampahDto request) {
        dataSampahService.pembelianSampah(request.getListSampah());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/penjualan")
    public ResponseEntity<Void> penjualanSampah(@Valid @RequestBody HistorySampahDto request) {
        dataSampahService.penjualanSampah(request.getListSampah());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/history/pembelian")
    public ResponseEntity<ApiResponse<Map<String, Object>>> historyPembelian(
        @RequestParam(defaultValue = "id", name = "sortBy") String sortBy,
        @RequestParam(defaultValue = "ASC", name = "sortDirection") String sortDirection,
        @RequestParam(defaultValue = "1", name = "page") int page,
        @RequestParam(defaultValue = "10", name = "size") int size,
        @RequestParam(required = false, name = "search") String search
    ) {
        Map<String, Object> historyPembelian = dataSampahService.getAllHistoryPembelian(
            sortBy,
            sortDirection,
            page,
            size,
            search
        );

        return new ResponseEntity<>(new ApiResponse<>(historyPembelian), HttpStatus.OK);
    }

    @GetMapping("/history/penjualan")
    public ResponseEntity<ApiResponse<List<HistoryPenjualan>>> historyPenjualan() {
        List<HistoryPenjualan> historyPenjualan = dataSampahService.getAllHistoryPenjualan();

        return new ResponseEntity<>(new ApiResponse<>(historyPenjualan), HttpStatus.OK);
    }

    @GetMapping("/history/pembelian/{id}")
    public ResponseEntity<ApiResponse<HistoryPembelian>> detailHistoryPembelian(@PathVariable("id") Long id) {
        HistoryPembelian detailHistoryPembelian = dataSampahService.getDetailListPembelian(id);

        return new ResponseEntity<>(new ApiResponse<>(detailHistoryPembelian), HttpStatus.OK);
    }

    @GetMapping("/history/penjualan/{id}")
    public ResponseEntity<ApiResponse<HistoryPenjualan>> detailHistoryPenjualan(@PathVariable("id") Long id) {
        HistoryPenjualan dHistoryPenjualan = dataSampahService.getDetailListPenjualan(id);

        return new ResponseEntity<>(new ApiResponse<>(dHistoryPenjualan), HttpStatus.OK);
    }
    
}
