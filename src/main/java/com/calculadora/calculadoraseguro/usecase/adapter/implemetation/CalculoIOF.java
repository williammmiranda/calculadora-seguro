package com.calculadora.calculadoraseguro.usecase.adapter.implemetation;

import com.calculadora.calculadoraseguro.usecase.adapter.Calculo;

import java.math.BigDecimal;

public class CalculoIOF implements Calculo {

    private BigDecimal taxaIOF;

    public CalculoIOF(BigDecimal taxaIOF) {
        this.taxaIOF = taxaIOF;
    }

    @Override
    public BigDecimal calcular(BigDecimal precoBase) {
        return precoBase.multiply(taxaIOF);
    }
}