import { Routes } from '@angular/router';
import { BeneficioListComponent } from './components/beneficio-list/beneficio-list';
import { TransferenciaFormComponent } from './components/transferencia-form/transferencia-form';

export const routes: Routes = [
  { path: '', redirectTo: 'beneficios', pathMatch: 'full' },
  { path: 'beneficios', component: BeneficioListComponent },
  { path: 'transferir', component: TransferenciaFormComponent },
  { path: '**', redirectTo: 'beneficios' }
];