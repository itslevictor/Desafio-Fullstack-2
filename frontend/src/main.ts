import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app'; // Certifique-se que o nome é AppComponent

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));