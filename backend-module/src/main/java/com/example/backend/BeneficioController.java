package com.example.backend;

import com.example.ejb.BeneficioEjbService;
import com.example.ejb.model.Beneficio; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
@CrossOrigin(origins = "http://localhost:4200")
public class BeneficioController {

    @Autowired
    private BeneficioEjbService beneficioService;

    @PostMapping("/transferir")
    public ResponseEntity<String> transferir(@RequestBody TransferenciaDTO dto) {
        try {
            beneficioService.transferir(dto.getOrigemId(), dto.getDestinoId(), dto.getValor());
            return ResponseEntity.ok("Transferência realizada com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Beneficio> criar(@RequestBody Beneficio beneficio) {
        try {
            Beneficio novo = beneficioService.salvar(beneficio);
            return ResponseEntity.status(201).body(novo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Beneficio>> listarTodos() {
        List<Beneficio> lista = beneficioService.listarTodos();
        return ResponseEntity.ok(lista);
    }
}

class TransferenciaDTO {
    private Long origemId;
    private Long destinoId;
    private Double valor;

    public Long getOrigemId() { return origemId; }
    public void setOrigemId(Long origemId) { this.origemId = origemId; }
    public Long getDestinoId() { return destinoId; }
    public void setDestinoId(Long destinoId) { this.destinoId = destinoId; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
}