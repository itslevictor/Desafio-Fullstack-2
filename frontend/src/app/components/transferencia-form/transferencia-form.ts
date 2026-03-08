import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { BeneficioService } from '../../services/beneficio.service';
import { Beneficio } from '../../models/beneficio.model';

@Component({
  selector: 'app-transferencia-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './transferencia-form.html'
})
export class TransferenciaFormComponent implements OnInit {
  beneficios: Beneficio[] = [];
  transferencia = { origemId: 0, destinoId: 0, valor: 0 };
  erro: string = '';
  mensagem: string = '';

  constructor(private service: BeneficioService, private router: Router) {}

  ngOnInit() {
    this.service.getBeneficios().subscribe(dados => this.beneficios = dados);
  }

  onSubmit() {
    this.service.transferir(this.transferencia.origemId, this.transferencia.destinoId, this.transferencia.valor)
      .subscribe({
        next: () => {
          this.mensagem = 'Transferência realizada!';
          setTimeout(() => this.router.navigate(['/beneficios']), 1500);
        },
        error: (err) => this.erro = err.error || 'Erro na operação.'
      });
  }
}