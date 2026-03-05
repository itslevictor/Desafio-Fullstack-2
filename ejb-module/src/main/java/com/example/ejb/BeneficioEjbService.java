package com.example.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityNotFoundException;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Regra de Negocio: Transferência entre contas de benefícios.
     * Correcao do Bug: Adicao de validacoes de saldo e garantia de integridade nas transacoes.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transferir(Long origemId, Long destinoId, Double valor) {
        
        // 1. Validação de parâmetros de entrada
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        // 2. Localização das entidades no banco de dados
        Beneficio origem = em.find(Beneficio.class, origemId);
        Beneficio destino = em.find(Beneficio.class, destinoId);

        // 3. Validação de existência (Prevenção de NullPointerException)
        if (origem == null) {
            throw new EntityNotFoundException("Conta de origem nao encontrada ID: " + origemId);
        }
        if (destino == null) {
            throw new EntityNotFoundException("Conta de destino nao encontrada ID: " + destinoId);
        }

        // 4. Validação de Saldo (Requisito Principal do Bug)
        if (origem.getSaldo() < valor) {
            // Lançar RuntimeException dentro de um EJB com transação REQUIRED
            // força o Rollback automático de qualquer alteração pendente.
            throw new RuntimeException("Saldo insuficiente. Operação cancelada.");
        }

        // 5. Execução da transferência
        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);

        // Sincronização com o contexto de persistencia
        em.merge(origem);
        em.merge(destino);
    }
}