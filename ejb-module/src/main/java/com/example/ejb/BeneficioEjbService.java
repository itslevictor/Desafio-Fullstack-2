package com.example.ejb;

import com.example.ejb.model.Beneficio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void transferir(Long origemId, Long destinoId, Double valor) {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        Beneficio origem = em.find(Beneficio.class, origemId);
        Beneficio destino = em.find(Beneficio.class, destinoId);

        if (origem == null || destino == null) {
            throw new EntityNotFoundException("Uma ou ambas as contas não foram encontradas.");
        }

        if (origem.getValor().doubleValue() < valor) {
            throw new IllegalStateException("Saldo insuficiente na conta origem.");
        }

        BigDecimal valorTransferencia = BigDecimal.valueOf(valor);
        origem.setValor(origem.getValor().subtract(valorTransferencia));
        destino.setValor(destino.getValor().add(valorTransferencia));
        
        try {
            em.merge(origem);
            em.merge(destino);
            em.flush(); 
        } catch (OptimisticLockException e) {
            throw new RuntimeException("Erro de concorrência: A conta foi alterada por outro processo.");
        }
    }

    // MÉTODO QUE O CONTROLLER ESTÁ BUSCANDO
    @Transactional
    public Beneficio salvar(Beneficio beneficio) {
        if (beneficio.getId() == null) {
            em.persist(beneficio);
            return beneficio;
        } else {
            return em.merge(beneficio);
        }
    }

    public List<Beneficio> listarTodos() {
        return em.createQuery("SELECT b FROM Beneficio b", Beneficio.class).getResultList();
    }
}