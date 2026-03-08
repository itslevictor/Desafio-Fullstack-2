import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BeneficioService } from '../../services/beneficio.service';
import { Transacao } from '../../models/beneficio.model';

@Component({
  selector: 'app-beneficio-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './beneficio-list.html',
  styleUrl: './beneficio-list.scss'
})
export class BeneficioListComponent implements OnInit {
  // Alterado de 'beneficios' para 'transacoes' para bater com o HTML
  transacoes: Transacao[] = [];

  constructor(private service: BeneficioService) {}

  ngOnInit(): void {
    this.carregarHistorico();
  }

  carregarHistorico() {
    this.service.getHistorico().subscribe({
      next: (dados) => {
        this.transacoes = dados;
      },
      error: (err) => {
        console.error('Erro ao buscar histórico de transações', err);
      }
    });
  }
}