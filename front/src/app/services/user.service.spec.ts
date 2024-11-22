import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { UserService } from './user.service';
import { User } from '../interfaces/user.interface';

describe('UserService', () => {
  let service: UserService;
  let mockHttpClient: any;

  const mockUser: User = {
    id: 1,
    email: 'test@example.com',
    lastName: 'Doe',
    firstName: 'John',
    admin: true,
    password: 'hashed_password',
    createdAt: new Date('2021-01-01'),
    updatedAt: new Date('2023-01-01')
  };

  beforeEach(() => {
    mockHttpClient = {
      get: jest.fn(),
      delete: jest.fn()
    };

    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        { provide: HttpClient, useValue: mockHttpClient },
        UserService
      ]
    });

    service = TestBed.inject(UserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return user details from getById()', () => {
    const userId = '1';
    
    // Arrange: Mock the HTTP call
    mockHttpClient.get.mockReturnValue(of(mockUser));

    // Act: Call the getById() method
    service.getById(userId).subscribe(user => {
      // Assert: Verify that the method returns the expected data
      expect(user).toEqual(mockUser);
      expect(mockHttpClient.get).toHaveBeenCalledWith(`api/user/${userId}`);
    });
  });

  it('should call delete() and delete user', () => {
    const userId = '1';
    
    // Arrange: Mock the HTTP call
    mockHttpClient.delete.mockReturnValue(of(null));

    // Act: Call the delete() method
    service.delete(userId).subscribe(response => {
      // Assert: Verify that the method was called
      expect(response).toBeNull();  // The delete method typically returns no data
      expect(mockHttpClient.delete).toHaveBeenCalledWith(`api/user/${userId}`);
    });
  });
});
