import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from '../../services/auth.service';

import { LoginComponent } from './login.component';
import { of, throwError } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { Router } from '@angular/router';

describe('LoginComponent', () => {

  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService, AuthService],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should login success', () => {

    authService = TestBed.inject(AuthService);
    const authServiceMock = jest.spyOn(authService, "login").mockImplementation(() => of({} as SessionInformation));
    router = TestBed.inject(Router);
    const routerMock = jest.spyOn(router, "navigate").mockImplementation(async () => true);
    const sessionServiceMock = jest.spyOn(TestBed.inject(SessionService), "logIn").mockImplementation();

    component.submit();

    expect(sessionServiceMock).toHaveBeenCalled();
    expect(authServiceMock).toHaveBeenCalled();
    expect(routerMock).toHaveBeenCalledWith(['/sessions']);

  });

  it('should login with error', () => {

    authService = TestBed.inject(AuthService);
    const authServiceMock = jest.spyOn(authService, "login").mockImplementation(throwError);

    component.submit();

    expect(component.onError).toBeTruthy;

  });

});