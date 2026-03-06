package com.example.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Regra de Negócio Avançada: Transferência com Optimistic Locking.
     * O uso do campo 'version' no banco garante que se o saldo for alterado 
     * entre a leitura e a gravação, uma OptimisticLockException será lançada.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transferir(Long origemId, Long destinoId, Double valor) {
        
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        // Busca as entidades - O JPA automaticamente verificará a versão no commit
        Beneficio origem = em.find(Beneficio.class, origemId);
        Beneficio destino = em.find(Beneficio.class, destinoId);

        if (origem == null || destino == null) {
            throw new EntityNotFoundException("Uma ou ambas as contas não foram encontradas.");
        }

        if (origem.getSaldo() < valor) {
            // Lançar RuntimeException força o Rollback no EJB
            throw new IllegalStateException("Saldo insuficiente na conta origem.");
        }

        // Atualização dos estados
        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);

        // O merge processa as alterações e incrementa o campo 'version'
        try {
            em.merge(origem);
            em.merge(destino);
            em.flush(); // Força a verificação do lock antes do fim do método
        } catch (OptimisticLockException e) {
            throw new RuntimeException("Erro de concorrência: A conta foi alterada por outro processo. Tente novamente.");
        }
    }
}