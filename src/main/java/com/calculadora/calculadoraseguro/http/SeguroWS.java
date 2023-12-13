package com.calculadora.calculadoraseguro.http;

import com.calculadora.calculadoraseguro.http.domain.SeguroCalculadoDTO;
import com.calculadora.calculadoraseguro.http.domain.SeguroTO;
import com.calculadora.calculadoraseguro.usecase.AtualizarSeguro;
import com.calculadora.calculadoraseguro.usecase.BuscarSeguro;
import com.calculadora.calculadoraseguro.usecase.CriarSeguro;
import com.calculadora.calculadoraseguro.usecase.ExcluirSeguro;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.calculadora.calculadoraseguro.http.URLMapping.SEGURO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = SEGURO)
@AllArgsConstructor
@Api(tags = "Seguros", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class SeguroWS {

    private final CriarSeguro criarSeguro;
    private final BuscarSeguro buscarSeguro;
    private final AtualizarSeguro atualizarSeguro;
    private final ExcluirSeguro excluirSeguro;

    @PostMapping
    @ApiOperation(value = "Cria um seguro novo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Seguro criado com sucesso"),
            @ApiResponse(code = 400, message = "Preço Base não enviado para o calculo"),
            @ApiResponse(code = 500, message = "Erro ao criar o seguro")
    })
    public ResponseEntity<SeguroCalculadoDTO> criarSeguro(@RequestBody SeguroTO seguroRequest){
        var seguroResponse = criarSeguro.executar(seguroRequest);
        return ResponseEntity.created(URI.create("/api/seguros/" + seguroResponse.getId())).body(seguroResponse);
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Buscar Seguro pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Seguro encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Seguro não encontrado"),
            @ApiResponse(code = 500, message = "Erro ao buscar o seguro")
    })
    public ResponseEntity<SeguroCalculadoDTO>  buscarSeguroPorId(@PathVariable String id){
        return ResponseEntity.ok().body(buscarSeguro.executar(id));
    }


    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Atualiza informações de um seguro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Seguro atualizado com sucesso"),
            @ApiResponse(code = 500, message = "Erro ao atualizar o seguro")
    })
    public ResponseEntity<SeguroCalculadoDTO> atualizarSeguro(@PathVariable String id,
                                                              @RequestBody SeguroTO seguroRequest){

        return ResponseEntity.ok().body(atualizarSeguro.executar(seguroRequest, id));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Excluí o seguro por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Seguro excluído com sucesso"),
            @ApiResponse(code = 404, message = "Seguro não encontrado"),
            @ApiResponse(code = 500, message = "Erro ao excluir o seguro")
    })
    public ResponseEntity<String> excluirSeguro(@PathVariable String id) {
        excluirSeguro.executar(id);
        return ResponseEntity.ok("Seguro excluído com sucesso");
    }

}
