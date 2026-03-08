import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BeneficioService } from './services/beneficio.service';
import { Beneficio } from './models/beneficio.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.scss'
})
export class AppComponent implements OnInit {
  logado: boolean = false;
  showModal: boolean = false;
  
  loginInput: string = '';
  senhaInput: string = '';
  feedbackMsg: string = '';
  feedbackStatus: string | null = null;
  
  contaAtiva: any = { nome: 'Administrador BIP' };
  beneficios: Beneficio[] = [];

  constructor(private beneficioService: BeneficioService) {}

  ngOnInit(): void {
    // Carregamento inicial pode ser feito aqui se necessário
  }

  login() {
    if (this.loginInput === 'admin' && this.senhaInput === 'admin') {
      this.logado = true;
      this.carregarDados();
      this.setFeedback('Bem-vindo ao BIP Digital!', 'success');
    } else {
      this.setFeedback('Credenciais inválidas. Use admin/admin.', 'error');
    }
  }

  logout() {
    this.logado = false;
    this.loginInput = '';
    this.senhaInput = '';
    this.beneficios = [];
    this.showModal = false;
  }

  carregarDados() {
    this.beneficioService.listar().subscribe({
      next: (dados) => { this.beneficios = dados; },
      error: () => { this.setFeedback('Erro ao conectar com o servidor.', 'error'); }
    });
  }

  setFeedback(msg: string, status: 'success' | 'error') {
    this.feedbackMsg = msg;
    this.feedbackStatus = status;
    setTimeout(() => { this.feedbackStatus = null; }, 4000);
  }

  exportarPDF() {
    window.print();
  }
}