package com.example.ejb;

import com.example.ejb.model.Beneficio;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import java.util.List;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Regra de Negócio Avançada: Transferência com Optimistic Locking.
     * O uso do campo 'version' garante proteção contra Dirty Reads.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transferir(Long origemId, Long destinoId, Double valor) {
        
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        // Busca as entidades - O JPA verificará a versão no commit
        Beneficio origem = em.find(Beneficio.class, origemId);
        Beneficio destino = em.find(Beneficio.class, destinoId);

        if (origem == null || destino == null) {
            throw new EntityNotFoundException("Uma ou ambas as contas não foram encontradas.");
        }

        // Note: Se na sua classe Beneficio o campo for 'valor', altere para getValor()
        if (origem.getValor().doubleValue() < valor) {
            throw new IllegalStateException("Saldo insuficiente na conta origem.");
        }

        // Atualização dos estados usando BigDecimal para precisão financeira
        origem.setValor(origem.getValor().subtract(java.math.BigDecimal.valueOf(valor)));
        destino.setValor(destino.getValor().add(java.math.BigDecimal.valueOf(valor)));

        try {
            em.merge(origem);
            em.merge(destino);
            em.flush(); 
        } catch (OptimisticLockException e) {
            throw new RuntimeException("Erro de concorrência: A conta foi alterada por outro processo.");
        }
    }
}