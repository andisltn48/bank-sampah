package com.babylo.banksampah.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babylo.banksampah.dto.DataSampahDto;
import com.babylo.banksampah.entities.DataSampah;
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
}
