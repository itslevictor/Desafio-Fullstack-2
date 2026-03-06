import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; // Importação necessária
import { Observable } from 'rxjs';
import { Beneficio } from '../models/beneficio.model';

@Injectable({ providedIn: 'root' })
export class BeneficioService {
  private apiUrl = 'http://localhost:8082/api/v1/beneficios';

  constructor(private http: HttpClient) {}

  listar(): Observable<Beneficio[]> {
    // Faz a chamada GET real para o backend
    return this.http.get<Beneficio[]>(this.apiUrl);
  }

  transferir(origemId: number, destinoId: number, valor: number): Observable<any> {
    const payload = { origemId, destinoId, valor };
    // Faz a chamada POST para o endpoint de transferência
    return this.http.post(`${this.apiUrl}/transferir`, payload);
  }
}