import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-transferencia-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './transferencia-form.html',
  styleUrl: './transferencia-form.scss'
})
export class TransferenciaFormComponent {
  // Objeto para o formulário (Essência do requisito CRUD)
  transferencia = {
    origemId: null,
    destinoId: null,
    valor: 0
  };

  onSubmit() {
    console.log('Dados da transferência:', this.transferencia);
    // a lógica de integração com o backend futuramente
  }
}