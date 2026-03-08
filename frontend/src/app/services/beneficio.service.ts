import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficio, Transacao, TransferenciaDTO } from '../models/beneficio.model';

@Injectable({ providedIn: 'root' })
export class BeneficioService {
  private apiUrl = 'http://localhost:8082/api/v1/beneficios';

  constructor(private http: HttpClient) {}

  // Retorna a lista de contas/benefícios para o select do formulário
  listar(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.apiUrl);
  }

  // Retorna o histórico de transações para a página inicial
  getHistorico(): Observable<Transacao[]> {
    return this.http.get<Transacao[]>(`${this.apiUrl}/historico`);
  }

  transferir(origemId: number, destinoId: number, valor: number): Observable<any> {
    const payload: TransferenciaDTO = { origemId, destinoId, valor };
    return this.http.post(`${this.apiUrl}/transferir`, payload);
  }
}