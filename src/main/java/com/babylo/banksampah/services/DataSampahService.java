package com.babylo.banksampah.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babylo.banksampah.dto.DataSampahDto;
import com.babylo.banksampah.entities.DataSampah;
import com.babylo.banksampah.exception.DataNotFoundException;
import com.babylo.banksampah.repositories.DataSampahRepository;

@Service
public class DataSampahService {

    @Autowired
    private DataSampahRepository dataSampahRepository;

    public DataSampah addDataSampah(DataSampahDto dataSampahDto) {
        DataSampah dataSampah = new DataSampah();

        dataSampah.setNamaSampah(dataSampahDto.getNamaSampah());
        dataSampah.setHargaBeli(dataSampahDto.getHargaBeli());
        dataSampah.setHargaJual(dataSampahDto.getHargaJual());
        dataSampah.setUnit(dataSampahDto.getUnit());

        return dataSampahRepository.save(dataSampah);
    }

    public List<DataSampah> getAllDataSampah() {
        return dataSampahRepository.findAll();
    }

    public DataSampah getDetailSampah(Long id) {
        DataSampah dataSampah = dataSampahRepository.findById(id).orElseThrow(
            () -> new DataNotFoundException("Data sampah tidak ditemukan")
        );

        return dataSampah;
    }

    public DataSampah updateDataSampah(Long id, DataSampahDto dataSampahDto) {
        DataSampah dataSampah = dataSampahRepository.findById(id).orElseThrow(
            () -> new DataNotFoundException("Data sampah tidak ditemukan")
        );

        dataSampah.setNamaSampah(dataSampahDto.getNamaSampah());
        dataSampah.setHargaBeli(dataSampahDto.getHargaBeli());
        dataSampah.setHargaJual(dataSampahDto.getHargaJual());
        dataSampah.setUnit(dataSampahDto.getUnit());

        return dataSampahRepository.save(dataSampah);
    }

    public void deleteDataSampah(Long id) {
        DataSampah dataSampah = dataSampahRepository.findById(id).orElseThrow(
            () -> new DataNotFoundException("Data sampah tidak ditemukan")
        );

        dataSampahRepository.deleteById(dataSampah.getId());
    }
}
