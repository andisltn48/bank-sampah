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
import com.babylo.banksampah.entities.ListHistoryPembelian;
import com.babylo.banksampah.entities.ListHistoryPenjualan;
import com.babylo.banksampah.exception.DataNotFoundException;
import com.babylo.banksampah.repositories.DataSampahRepository;
import com.babylo.banksampah.repositories.HistoryPembelianRepository;
import com.babylo.banksampah.repositories.HistoryPenjualanRepository;
import com.babylo.banksampah.repositories.ListHistoryPembelianRepository;
import com.babylo.banksampah.repositories.ListHistoryPenjualanRepository;

import jakarta.transaction.Transactional;

@Service
public class DataSampahService {

    @Autowired
    private DataSampahRepository dataSampahRepository;

    @Autowired
    private HistoryPembelianRepository historyPembelianRepository;

    @Autowired
    private ListHistoryPembelianRepository ListHistoryPembelianRepository;

    @Autowired
    private HistoryPenjualanRepository historyPenjualanRepository;

    @Autowired
    private ListHistoryPenjualanRepository listHistoryPenjualanRepository;

    public DataSampah addDataSampah(DataSampahDto dataSampahDto) {
        DataSampah dataSampah = new DataSampah();

        dataSampah.setNamaSampah(dataSampahDto.getNamaSampah());
        dataSampah.setHargaBeli(dataSampahDto.getHargaBeli());
        dataSampah.setHargaJual(dataSampahDto.getHargaJual());
        dataSampah.setUnit(dataSampahDto.getUnit());

        return dataSampahRepository.save(dataSampah);
    }

    public Map<String, Object> getAllDataSampah(
            String sortBy,
            String sortDirection,
            Integer page,
            Integer size,
            String searchText
    ) {
        page = page != null ? page - 1 : 0;
        
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id"; // Default sort column
        }

        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "ASC"; // Default sort direction
        }

        // Ensure valid sort direction (PostgreSQL expects 'ASC' or 'DESC')
        if (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC")) {
            sortDirection = "ASC"; // Default to 'ASC' if invalid
        }

        // Calculate the offset for pagination
        int offset = page * size;

        // Call repository to get the paginated, sorted, and searched products
        List<DataSampah> dataSampah = dataSampahRepository.findAllDataSampah(
                searchText,
                sortBy,
                sortDirection,
                size,
                offset
        );

        // Get total products count for pagination metadata
        long totalProducts = dataSampahRepository.countDataSampah(searchText);

        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalProducts / size);

        // Return response with products and pagination metadata
        Map<String, Object> response = new HashMap<>();
        response.put("data", dataSampah);
        response.put("totalItems", totalProducts);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);
        response.put("pageSize", size);

        return response;
    }

    public DataSampah getDetailSampah(Long id) {
        DataSampah dataSampah = dataSampahRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Data sampah tidak ditemukan"));

        return dataSampah;
    }

    public DataSampah updateDataSampah(Long id, DataSampahDto dataSampahDto) {
        DataSampah dataSampah = dataSampahRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Data sampah tidak ditemukan"));

        dataSampah.setNamaSampah(dataSampahDto.getNamaSampah());
        dataSampah.setHargaBeli(dataSampahDto.getHargaBeli());
        dataSampah.setHargaJual(dataSampahDto.getHargaJual());
        dataSampah.setUnit(dataSampahDto.getUnit());

        return dataSampahRepository.save(dataSampah);
    }

    public void deleteDataSampah(Long id) {
        DataSampah dataSampah = dataSampahRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Data sampah tidak ditemukan"));

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

            Float jumlah = (float) 0;
            if (valueJumlah instanceof Double) {
                jumlah = ((Double) valueJumlah).floatValue();
            } else if (valueJumlah instanceof Integer) {
                jumlah = ((Integer) valueJumlah).floatValue();
            }
            // Float jumlah = ((Double) map.get("jumlah")).floatValue();
            Long totalHarga = ((Integer) Math.round(existDataSampah.getHargaBeli() * jumlah)).longValue();
            ListHistoryPembelian ListHistoryPembelian = new ListHistoryPembelian();

            ListHistoryPembelian.setHistoryPembelian(savedHistoryPembelian);
            ListHistoryPembelian.setDataSampah(existDataSampah);
            ListHistoryPembelian.setJumlah(jumlah);
            ListHistoryPembelian.setHarga(totalHarga);
            ListHistoryPembelianRepository.save(ListHistoryPembelian);

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

            Float jumlah = (float) 0;
            if (valueJumlah instanceof Double) {
                jumlah = ((Double) valueJumlah).floatValue();
            } else if (valueJumlah instanceof Integer) {
                jumlah = ((Integer) valueJumlah).floatValue();
            }
            // Float jumlah = ((Double) map.get("jumlah")).floatValue();
            Long totalHarga = ((Integer) Math.round(existDataSampah.getHargaBeli() * jumlah)).longValue();
            ListHistoryPenjualan listHistoryPenjualan = new ListHistoryPenjualan();

            listHistoryPenjualan.setHistoryPenjualan(savedHistoryPenjualan);
            listHistoryPenjualan.setDataSampah(existDataSampah);
            listHistoryPenjualan.setJumlah(jumlah);
            listHistoryPenjualan.setHarga(totalHarga);
            listHistoryPenjualanRepository.save(listHistoryPenjualan);

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
    public HistoryPembelian getDetailListPembelian(Long idHistory) {
        HistoryPembelian historyPembelian = historyPembelianRepository.findById(idHistory)
                .orElseThrow(() -> new DataNotFoundException("History pembelian tidak ditemukan"));

        return historyPembelian;
    }

    @Transactional
    public HistoryPenjualan getDetailListPenjualan(Long idHistory) {
        HistoryPenjualan historyPenjualan = historyPenjualanRepository.findById(idHistory)
                .orElseThrow(() -> new DataNotFoundException("History penjualan tidak ditemukan"));

        return historyPenjualan;
    }
}
