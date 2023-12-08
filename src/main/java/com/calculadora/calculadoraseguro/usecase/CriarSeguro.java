package com.calculadora.calculadoraseguro.usecase;

import com.calculadora.calculadoraseguro.gateway.converter.SeguroCalculadoTOToSeguroEntityConverter;
import com.calculadora.calculadoraseguro.gateway.converter.SeguroEntityToSeguroCalculadoTOConverter;
import com.calculadora.calculadoraseguro.gateway.entity.SeguroEntity;
import com.calculadora.calculadoraseguro.gateway.service.SeguroService;
import com.calculadora.calculadoraseguro.http.domain.SeguroCalculadoDTO;
import com.calculadora.calculadoraseguro.http.domain.SeguroTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CriarSeguro {
    private final SeguroEntityToSeguroCalculadoTOConverter seguroEntityToSeguroCalculadoTOConverter;
    private final SeguroCalculadoTOToSeguroEntityConverter seguroCalculadoTOToSeguroEntityConverter;
    private final SeguroService seguroService;
    private final CalcularPrecoSeguro calcularPrecoSeguro;

    public SeguroCalculadoDTO executar(SeguroTO seguroTO) {

        var seguroCalculadoTO = gerarSeguroCalculadoTO(seguroTO);

        var seguroEntity = salvarSeguro(seguroCalculadoTO);

        return converterSeguroEntityParaCalculadoTO(seguroEntity);
    }

    private SeguroCalculadoDTO gerarSeguroCalculadoTO(SeguroTO seguroTO){
        var seguroCalculadoTO = new SeguroCalculadoDTO();

        seguroCalculadoTO.setNome(seguroTO.getNome());
        seguroCalculadoTO.setPrecoBase(seguroTO.getPrecoBase());
        seguroCalculadoTO.setSeguroCategoria(seguroTO.getSeguroCategoria());
        seguroCalculadoTO.setPrecoTarifado(calcularPrecoSeguro.executar(seguroTO.getPrecoBase(), seguroTO.getSeguroCategoria()));

        return seguroCalculadoTO;
    }

    private SeguroEntity salvarSeguro(SeguroCalculadoDTO seguroCalculadoDTO) {
        return seguroService.salvarSeguro(seguroCalculadoTOToSeguroEntityConverter.convert(seguroCalculadoDTO));
    }

    private SeguroCalculadoDTO converterSeguroEntityParaCalculadoTO(SeguroEntity seguroEntity) {
        return seguroEntityToSeguroCalculadoTOConverter.convert(seguroEntity);
    }
}