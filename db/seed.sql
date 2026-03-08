-- Insere apenas se a tabela estiver vazia (evita duplicatas em restarts)
INSERT INTO BENEFICIO (NOME, DESCRICAO, VALOR, ATIVO) 
SELECT 'Conta Corrente', 'Saldo principal para transações', 1500.00, TRUE
WHERE NOT EXISTS (SELECT 1 FROM BENEFICIO WHERE NOME = 'Conta Corrente');

INSERT INTO BENEFICIO (NOME, DESCRICAO, VALOR, ATIVO) 
SELECT 'Reserva de Emergência', 'Fundo de segurança', 5000.00, TRUE
WHERE NOT EXISTS (SELECT 1 FROM BENEFICIO WHERE NOME = 'Reserva de Emergência');