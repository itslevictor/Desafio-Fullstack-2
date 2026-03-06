export interface Beneficio {
  id?: number;
  nome: string;
  valor: number; 
  tipo?: string;
  ativo?: boolean;
}
  
  export interface TransferenciaDTO {
    origemId: number;
    destinoId: number;
    valor: number;
  }