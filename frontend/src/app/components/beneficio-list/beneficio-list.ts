import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

// Interface temporária para tipagem (Essência do modelo)
interface Beneficio {
  id: number;
  nome: string;
  tipo: string;
  saldo: number;
}

@Component({
  selector: 'app-beneficio-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './beneficio-list.html',
  styleUrl: './beneficio-list.scss'
})
export class BeneficioListComponent {
  // Dados simulados para validar a Feature 06
  beneficios: Beneficio[] = [
    { id: 1, nome: 'Vale Refeição', tipo: 'ALIMENTACAO', saldo: 550.00 },
    { id: 2, nome: 'Vale Transporte', tipo: 'TRANSPORTE', saldo: 200.00 },
    { id: 3, nome: 'Bônus Anual', tipo: 'OUTROS', saldo: 1200.50 }
  ];

  constructor() {}
}