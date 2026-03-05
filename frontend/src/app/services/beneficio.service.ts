import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Beneficio } from '../models/beneficio.model';

@Injectable({ providedIn: 'root' })
export class BeneficioService {
  // Mock de dados para validação da Feature 06
  private mockData: Beneficio[] = [
    { id: 1, nome: 'Vale Refeição', saldo: 550.00, tipo: 'REFEICAO' },
    { id: 2, nome: 'Vale Alimentação', saldo: 1200.00, tipo: 'ALIMENTACAO' }
  ];

  listar(): Observable<Beneficio[]> {
    return of(this.mockData);
  }

  transferir(origemId: number, destinoId: number, valor: number): Observable<boolean> {
    console.log(`Transferindo R$${valor} de ${origemId} para ${destinoId}`);
    return of(true); // Simula sucesso
  }
}