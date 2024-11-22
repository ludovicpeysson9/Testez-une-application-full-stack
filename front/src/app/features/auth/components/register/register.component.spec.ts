import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing'; // Import du RouterTestingModule pour simuler le router

import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';

describe('RegisterComponent', () => {

  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: AuthService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        RouterTestingModule // Ajout du RouterTestingModule pour mocker le Router
      ],
      providers: [
        AuthService // Fournisseur pour que TestBed puisse injecter AuthService
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should register with success', () => {

    authService = TestBed.inject(AuthService);
    const authServiceMock = jest.spyOn(authService, "register").mockImplementation(() => of(undefined));

    router = TestBed.inject(Router);
    const routerMock = jest.spyOn(router, "navigate").mockImplementation(async () => true);

    component.submit();

    expect(authServiceMock).toHaveBeenCalled();
    expect(routerMock).toHaveBeenCalledWith(['/login']);
  });

  it('should return error when trying to register', () => {

    authService = TestBed.inject(AuthService);
    const authServiceMock = jest.spyOn(authService, "register").mockImplementation(() => throwError(() => new Error('Registration failed')));

    component.submit();

    expect(component.onError).toBeTruthy(); // Correction du test
  });

});
