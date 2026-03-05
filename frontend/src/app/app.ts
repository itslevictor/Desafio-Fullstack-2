import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.scss'
})
export class AppComponent {
  logado = false;
  showModal = false;
  feedbackMsg = '';
  feedbackStatus: 'success' | 'error' | null = null;
  
  loginInput = '';
  senhaInput = '';
  destinatario = '';
  valorTransferir: number | null = null;

  // Base de dados local para a Feature 06
  contas: any = {
    'conta1': { nome: 'Usuário Alfa', saldo: 5000.00, historico: [] },
    'conta2': { nome: 'Usuário Beta', saldo: 2500.00, historico: [] }
  };

  contaAtiva: any = null;

  login() {
    const chave = this.loginInput.toLowerCase().trim();
    // Validação: chave existe e senha é igual ao input do usuário
    if (this.contas[chave] && this.senhaInput === this.loginInput) {
      this.contaAtiva = this.contas[chave];
      this.logado = true;
      this.feedbackStatus = null;
    } else {
      this.setFeedback('Usuário ou senha inválidos. Use conta1 ou conta2.', 'error');
    }
  }

  simularTransferencia() {
    const valor = this.valorTransferir || 0;
    const destinoChave = this.destinatario.toLowerCase().trim();
    const contaDestino = this.contas[destinoChave];

    if (valor > 0 && valor <= this.contaAtiva.saldo) {
      const timestamp = new Date().toLocaleString('pt-BR');
      
      const idDestino = contaDestino ? `${contaDestino.nome} (${destinoChave})` : this.destinatario;
      const idOrigem = `${this.contaAtiva.nome} (${this.loginInput.toLowerCase()})`;

      // Lógica de atualização dupla (Reatividade local)
      this.contaAtiva.saldo -= valor;
      this.contaAtiva.historico.unshift({
        data: timestamp,
        desc: `Enviado para: ${idDestino}`,
        valor: valor,
        tipo: 'SAIDA'
      });

      if (contaDestino && destinoChave !== this.loginInput.toLowerCase()) {
        contaDestino.saldo += valor;
        contaDestino.historico.unshift({
          data: timestamp,
          desc: `Recebido de: ${idOrigem}`,
          valor: valor,
          tipo: 'ENTRADA'
        });
      }

      this.setFeedback('Transferência realizada!', 'success');
      this.destinatario = '';
      this.valorTransferir = null;
    } else {
      this.setFeedback('Saldo insuficiente ou destinatário inválido.', 'error');
    }
  }
  setFeedback(msg: string, status: 'success' | 'error') {
    this.feedbackMsg = msg;
    this.feedbackStatus = status;
    
    // O uso de timeout já ajuda a evitar bloqueios na thread principal
    setTimeout(() => { 
      this.feedbackStatus = null; 
    }, 4000);
  }

  exportarPDF() {
    window.print();
  }

  logout() {
    this.logado = false;
    this.loginInput = '';
    this.senhaInput = '';
    this.contaAtiva = null;
    this.showModal = false;
  }
}

