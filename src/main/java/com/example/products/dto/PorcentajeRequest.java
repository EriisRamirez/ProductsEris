package com.example.products.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PorcentajeRequest {

    @NotNull
    @Min(0)
            @Max(100)
    Integer descuento;

    public PorcentajeRequest() {
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }
}
