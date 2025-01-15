package com.babylo.banksampah.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataSampahDto {

    @NotBlank
    private String namaSampah;
    
    @NotNull
    @Positive(message = "Amount must be greater than zero")
    private Long hargaBeli;

    @NotNull
    @Positive(message = "Amount must be greater than zero")
    private Long hargaJual;
    
    @NotBlank
    private String unit;
}
