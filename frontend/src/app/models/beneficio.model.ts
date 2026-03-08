export interface Beneficio {
  id: number;
  nome: string;
  descricao: string; 
  valor: number;     
  ativo: boolean;
  version?: number;
}

export interface Transacao {
  id?: number;
  data: string;
  tipo: string;
  descricao: string;
  valor: number;
}

export interface TransferenciaDTO {
  origemId: number;
  destinoId: number;
  valor: number;
}