package com.babylo.banksampah.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babylo.banksampah.dto.DataSampahDto;
import com.babylo.banksampah.entities.DataSampah;
import com.babylo.banksampah.entities.HistoryPembelian;
import com.babylo.banksampah.entities.ListHistorySampah;
import com.babylo.banksampah.exception.DataNotFoundException;
import com.babylo.banksampah.repositories.DataSampahRepository;
import com.babylo.banksampah.repositories.HistoryPembelianRepository;
import com.babylo.banksampah.repositories.ListHistorySampahRepository;

import jakarta.transaction.Transactional;

@Service
public class DataSampahService {

    @Autowired
    private DataSampahRepository dataSampahRepository;

    @Autowired
    private HistoryPembelianRepository historyPembelianRepository;

    @Autowired
    private ListHistorySampahRepository listHistorySampahRepository;

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

    @Transactional
    public void pembelianSampah(List<Map<String, Object>> listSampah) {
        HistoryPembelian historyPembelian = new HistoryPembelian();
        
        HistoryPembelian savedHistoryPembelian = historyPembelianRepository.save(historyPembelian);
        for (Map<String, Object> map : listSampah) {
            Long idSampah = ((Integer) map.get("id")).longValue();
            Optional<DataSampah> dataSampah = dataSampahRepository.findById(idSampah);
            if (dataSampah.isPresent() == false) {
                historyPembelianRepository.deleteById(savedHistoryPembelian.getId());
                throw new DataNotFoundException("Data sampah tidak ditemukan");
            }

            DataSampah existDataSampah = dataSampah.get();
            Object valueJumlah = map.get("jumlah");

            Float jumlah = (float)0;
            if (valueJumlah instanceof Double) {
                jumlah = ((Double) valueJumlah).floatValue();
            } else if (valueJumlah instanceof Integer) {
                jumlah = ((Integer) valueJumlah).floatValue(); 
            }
            // Float jumlah = ((Double) map.get("jumlah")).floatValue();
            Long totalHarga = ((Integer) Math.round(existDataSampah.getHargaBeli() * jumlah)).longValue();
            ListHistorySampah listHistorySampah = new ListHistorySampah();

            listHistorySampah.setIdHistory(savedHistoryPembelian.getId());
            listHistorySampah.setIdSampah(idSampah);
            listHistorySampah.setJumlah(jumlah);
            listHistorySampah.setHarga(totalHarga);
            listHistorySampahRepository.save(listHistorySampah);
        }
    }
}
