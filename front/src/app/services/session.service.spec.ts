import { TestBed } from '@angular/core/testing';
import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { BehaviorSubject } from 'rxjs';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log in a user and update session information', () => {
    const mockSessionInfo: SessionInformation = {
      token: 'sample-token',
      type: 'Bearer',
      id: 1,
      username: 'john_doe',
      firstName: 'John',
      lastName: 'Doe',
      admin: true
    };

    service.logIn(mockSessionInfo);

    // Vérifie que isLogged est mis à jour
    expect(service.isLogged).toBe(true);
    // Vérifie que sessionInformation est mise à jour
    expect(service.sessionInformation).toEqual(mockSessionInfo);
  });

  it('should log out and clear session information', () => {
    // Log in un utilisateur d'abord
    const mockSessionInfo: SessionInformation = {
      token: 'sample-token',
      type: 'Bearer',
      id: 1,
      username: 'john_doe',
      firstName: 'John',
      lastName: 'Doe',
      admin: true
    };
    service.logIn(mockSessionInfo);

    // Maintenant log out
    service.logOut();

    // Vérifie que isLogged est mis à false
    expect(service.isLogged).toBe(false);
    // Vérifie que sessionInformation est undefined
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should emit the correct isLogged value via $isLogged observable', (done) => {
    const mockSessionInfo: SessionInformation = {
      token: 'sample-token',
      type: 'Bearer',
      id: 1,
      username: 'john_doe',
      firstName: 'John',
      lastName: 'Doe',
      admin: true
    };

    // Abonne-toi à l'observable et vérifie les valeurs émises
    service.$isLogged().subscribe((isLogged) => {
      expect(isLogged).toBe(true);
      done();
    });

    service.logIn(mockSessionInfo); // Cette action devrait déclencher l'émission de la valeur
  });

});
