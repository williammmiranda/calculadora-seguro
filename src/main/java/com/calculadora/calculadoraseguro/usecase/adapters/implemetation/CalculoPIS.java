package com.calculadora.calculadoraseguro.usecase.adapters.implemetation;

import com.calculadora.calculadoraseguro.usecase.adapters.Calculo;

import java.math.BigDecimal;

public class CalculoPIS implements Calculo {
    private BigDecimal taxaPIS;

    public CalculoPIS(BigDecimal taxaPIS) {
        this.taxaPIS = taxaPIS;
    }

    @Override
    public BigDecimal calcular(BigDecimal precoBase) {
        return precoBase.multiply(taxaPIS);
    }
}