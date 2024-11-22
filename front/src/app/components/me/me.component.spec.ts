import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { User } from '../../interfaces/user.interface';
import { UserService } from '../../services/user.service';
import { SessionService } from '../../services/session.service';
import { MeComponent } from './me.component';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let mockUserService: any;
  let mockRouter: any;
  let mockSnackBar: any;
  let mockSessionService: any;

  beforeEach(async () => {
    // Mock du UserService
    mockUserService = {
      getById: jest.fn().mockReturnValue(
        of({
          id: 1,
          email: 'test@example.com',
          firstName: 'Test',
          lastName: 'User',
          admin: true,
          password: 'hashed_password',
          createdAt: new Date('2023-01-01T00:00:00Z'),
          updatedAt: new Date('2023-06-01T00:00:00Z'),
        } as User)
      ),
      delete: jest.fn().mockReturnValue(of({})), // Mock de la suppression
    };

    // Mock du SessionService
    mockSessionService = {
      sessionInformation: { id: 1, admin: true },
      logOut: jest.fn(), // Mock du logOut
    };

    // Mock du Router
    mockRouter = {
      navigate: jest.fn(),
    };

    // Mock du MatSnackBar
    mockSnackBar = {
      open: jest.fn(),
    };

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: UserService, useValue: mockUserService },
        { provide: Router, useValue: mockRouter },
        { provide: MatSnackBar, useValue: mockSnackBar },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch user details on init', () => {
    component.ngOnInit();
    expect(mockUserService.getById).toHaveBeenCalledWith('1');
    expect(component.user).toEqual({
      id: 1,
      email: 'test@example.com',
      firstName: 'Test',
      lastName: 'User',
      admin: true,
      password: 'hashed_password',
      createdAt: new Date('2023-01-01T00:00:00Z'),
      updatedAt: new Date('2023-06-01T00:00:00Z'),
    });
  });

  it('should call history.back() when back method is called', () => {
    jest.spyOn(window.history, 'back');
    component.back();
    expect(window.history.back).toHaveBeenCalled();
  });

  it('should delete user and navigate to home on delete', fakeAsync(() => {
    // Appel de la méthode delete
    component.delete();

    // Utilisation de fakeAsync et tick() pour simuler les observables
    tick(1000); // simule l'écoulement du temps nécessaire pour l'observable
    fixture.detectChanges();

    // Vérification que la méthode delete du service utilisateur est bien appelée
    expect(mockUserService.delete).toHaveBeenCalledWith('1');

    // Vérification que le snack bar a bien été affiché
    expect(mockSnackBar.open).toHaveBeenCalledWith(
      'Your account has been deleted !',
      'Close',
      { duration: 3000 }
    );

    // Vérification que logOut a bien été appelé après la suppression
    //expect(mockSessionService.logOut).toHaveBeenCalled();

    // Vérification que la navigation a bien été faite vers la racine
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/']);
  }));
});
