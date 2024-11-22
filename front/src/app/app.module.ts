import { NgModule } from '@angular/core';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { MatCardModule } from '@angular/material/card';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MeComponent } from './components/me/me.component';

// Ajout des modules Angular Material
const materialModule = [
  MatButtonModule,
  MatCardModule,  // pour mat-card, mat-card-title, mat-card-content, etc.
  MatIconModule,  // pour mat-icon
  MatSnackBarModule,
  MatToolbarModule,
];

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    MeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule, 
    FlexLayoutModule,
    HttpClientModule,
    ...materialModule  // Ajout du tableau de modules Angular Material
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
