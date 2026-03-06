package com.example.backend;

import com.example.ejb.BeneficioEjbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    // Em um ambiente real JEE, usaríamos @EJB ou Injeção via JNDI
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
    @GetMapping
    public ResponseEntity<List<Beneficio>> listarTodos() {
        // Busca real do banco através do contexto de persistência
        List<Beneficio> lista = em.createQuery("SELECT b FROM Beneficio b", Beneficio.class).getResultList();
    return ResponseEntity.ok(lista);
    }
}

// DTO para receber o JSON do Angular
class TransferenciaDTO {
    private Long origemId;
    private Long destinoId;
    private Double valor;
    // Getters e Setters necessários
    public Long getOrigemId() { return origemId; }
    public Long getDestinoId() { return destinoId; }
    public Double getValor() { return valor; }
}

