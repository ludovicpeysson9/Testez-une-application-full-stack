import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { TeacherService } from './teacher.service';
import { Teacher } from '../interfaces/teacher.interface';

describe('TeacherService', () => {
  let service: TeacherService;
  let mockHttpClient: any;

  const mockTeachers: Teacher[] = [
    {
      id: 1,
      lastName: 'Doe',
      firstName: 'John',
      createdAt: new Date('2022-01-01'),
      updatedAt: new Date('2023-01-01')
    },
    {
      id: 2,
      lastName: 'Smith',
      firstName: 'Jane',
      createdAt: new Date('2021-06-15'),
      updatedAt: new Date('2022-07-20')
    }
  ];

  beforeEach(() => {
    mockHttpClient = {
      get: jest.fn()
    };

    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        { provide: HttpClient, useValue: mockHttpClient },
        TeacherService
      ]
    });

    service = TestBed.inject(TeacherService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return list of teachers from all()', () => {
    // Arrange: Mock the HTTP call
    mockHttpClient.get.mockReturnValue(of(mockTeachers));

    // Act: Call the all() method
    service.all().subscribe(teachers => {
      // Assert: Verify that the method returns the expected data
      expect(teachers).toEqual(mockTeachers);
      expect(mockHttpClient.get).toHaveBeenCalledWith('api/teacher');
    });
  });

  it('should return teacher details from detail()', () => {
    const teacherId = '1';
    const mockTeacher: Teacher = {
      id: 1,
      lastName: 'Doe',
      firstName: 'John',
      createdAt: new Date('2022-01-01'),
      updatedAt: new Date('2023-01-01')
    };

    // Arrange: Mock the HTTP call
    mockHttpClient.get.mockReturnValue(of(mockTeacher));

    // Act: Call the detail() method
    service.detail(teacherId).subscribe(teacher => {
      // Assert: Verify that the method returns the expected data
      expect(teacher).toEqual(mockTeacher);
      expect(mockHttpClient.get).toHaveBeenCalledWith(`api/teacher/${teacherId}`);
    });
  });
});
