import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

describe('AuthService', () => {
    let service: AuthService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [AuthService]
        });

        service = TestBed.inject(AuthService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should register a user', () => {
        const registerRequest: RegisterRequest = {
            email: 'test@example.com',
            firstName: 'Test',
            lastName: 'User',
            password: 'Password123'
        };

        service.register(registerRequest).subscribe(response => {
            expect(response).toBeUndefined(); // Assuming no body is returned
        });

        const req = httpMock.expectOne('api/auth/register');
        expect(req.request.method).toBe('POST');
        expect(req.request.body).toEqual(registerRequest);
        req.flush(null);  // Simulate a successful response with no content
    });

    it('should login a user', () => {
        const loginRequest: LoginRequest = {
            email: 'test@example.com',   // Cette ligne ne pose pas de problème car l'email est une donnée pour la demande
            password: 'Password123'
        };

        const sessionInfo: SessionInformation = {
            token: 'mock-token',
            type: 'Bearer',
            id: 1,
            username: 'test@example.com',  // Remplace "email" par "username"
            firstName: 'Test',
            lastName: 'User',
            admin: true
        };

        service.login(loginRequest).subscribe(response => {
            expect(response).toEqual(sessionInfo);
        });

        const req = httpMock.expectOne('api/auth/login');
        expect(req.request.method).toBe('POST');
        expect(req.request.body).toEqual(loginRequest);
        req.flush(sessionInfo);  // Simule un login réussi avec les infos de session
    });

    afterEach(() => {
        httpMock.verify();  // Ensures that no requests are pending after each test
    });
});
