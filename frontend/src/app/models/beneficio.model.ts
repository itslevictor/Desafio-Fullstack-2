export interface Beneficio {
    id: number;
    nome: string;
    saldo: number;
    tipo: string;
  }
  
  export interface TransferenciaDTO {
    origemId: number;
    destinoId: number;
    valor: number;
  }