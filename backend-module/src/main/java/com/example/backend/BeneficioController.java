package com.example.backend;

import com.example.ejb.BeneficioEjbService;
import com.example.ejb.model.Beneficio; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    @Autowired
    private BeneficioEjbService beneficioService;

    @PersistenceContext
    private EntityManager em;

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

    @GetMapping
    public ResponseEntity<List<Beneficio>> listarTodos() {
        // Busca real do banco através do contexto de persistência injetado
        List<Beneficio> lista = em.createQuery("SELECT b FROM Beneficio b", Beneficio.class).getResultList();
        return ResponseEntity.ok(lista);
    }
}

// DTO para receber o JSON do Angular
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