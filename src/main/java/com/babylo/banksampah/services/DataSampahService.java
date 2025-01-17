package com.babylo.banksampah.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babylo.banksampah.dto.DataSampahDto;
import com.babylo.banksampah.entities.DataSampah;
import com.babylo.banksampah.entities.HistoryPembelian;
import com.babylo.banksampah.entities.HistoryPenjualan;
import com.babylo.banksampah.entities.ListHistorySampah;
import com.babylo.banksampah.exception.DataNotFoundException;
import com.babylo.banksampah.repositories.DataSampahRepository;
import com.babylo.banksampah.repositories.HistoryPembelianRepository;
import com.babylo.banksampah.repositories.HistoryPenjualanRepository;
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
    
    @Autowired
    private HistoryPenjualanRepository historyPenjualanRepository;

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
        Long totalHargaPembelian = 0L;
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
            listHistorySampah.setType("Pembelian");
            listHistorySampahRepository.save(listHistorySampah);

            totalHargaPembelian += totalHarga;
        }
        savedHistoryPembelian.setTotalHarga(totalHargaPembelian);
        historyPembelianRepository.save(savedHistoryPembelian);
    }
    
    @Transactional
    public void penjualanSampah(List<Map<String, Object>> listSampah) {
        HistoryPenjualan historyPenjualan = new HistoryPenjualan();
        
        HistoryPenjualan savedHistoryPenjualan = historyPenjualanRepository.save(historyPenjualan);
        Long totalHargaPenjualan = 0L;
        for (Map<String, Object> map : listSampah) {
            Long idSampah = ((Integer) map.get("id")).longValue();
            Optional<DataSampah> dataSampah = dataSampahRepository.findById(idSampah);
            if (dataSampah.isPresent() == false) {
                historyPembelianRepository.deleteById(savedHistoryPenjualan.getId());
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

            listHistorySampah.setIdHistory(savedHistoryPenjualan.getId());
            listHistorySampah.setIdSampah(idSampah);
            listHistorySampah.setJumlah(jumlah);
            listHistorySampah.setHarga(totalHarga);
            listHistorySampah.setType("Penjualan");
            listHistorySampahRepository.save(listHistorySampah);

            totalHargaPenjualan += totalHarga;
        }

        savedHistoryPenjualan.setTotalHarga(totalHargaPenjualan);
        historyPenjualanRepository.save(savedHistoryPenjualan);
    }

    @Transactional
    public List<HistoryPembelian> getAllHistoryPembelian() {
        return historyPembelianRepository.findAll();
    }

    @Transactional
    public List<HistoryPenjualan> getAllHistoryPenjualan() {
        return historyPenjualanRepository.findAll();
    }

    @Transactional
    public Map<String, Object> getDetailListPembelian(Long idHistory) {
        HistoryPembelian historyPembelian = historyPembelianRepository.findById(idHistory).orElseThrow(() -> new DataNotFoundException("History pembelian tidak ditemukan"));

        String type = "Pembelian";
        List<ListHistorySampah> listHistorySampah = listHistorySampahRepository.findAllByIdHistoryAndType(idHistory, type);

        Map<String, Object> result = new HashMap<>();
        result.put("id", historyPembelian.getId());
        result.put("totalHarga", historyPembelian.getTotalHarga());
        result.put("createdAt", historyPembelian.getCreatedAt());
        result.put("updatedAt", historyPembelian.getUpdatedAt());
        result.put("history_sampah", listHistorySampah);

        return result;
    }

    @Transactional
    public Map<String, Object> getDetailListPenjualan(Long idHistory) {
        HistoryPenjualan historyPenjualan = historyPenjualanRepository.findById(idHistory).orElseThrow(() -> new DataNotFoundException("History penjualan tidak ditemukan"));

        String type = "Penjualan";
        List<ListHistorySampah> listHistorySampah = listHistorySampahRepository.findAllByIdHistoryAndType(idHistory, type);

        Map<String, Object> result = new HashMap<>();
        result.put("id", historyPenjualan.getId());
        result.put("totalHarga", historyPenjualan.getTotalHarga());
        result.put("createdAt", historyPenjualan.getCreatedAt());
        result.put("updatedAt", historyPenjualan.getUpdatedAt());
        result.put("history_sampah", listHistorySampah);

        return result;
    }
}
