import { Routes } from '@angular/router';
import { BeneficioListComponent } from './components/beneficio-list/beneficio-list'; // 
import { TransferenciaFormComponent } from './components/transferencia-form/transferencia-form'; // 

export const routes: Routes = [
  { path: '', component: BeneficioListComponent },
  { path: 'transferir', component: TransferenciaFormComponent }
];