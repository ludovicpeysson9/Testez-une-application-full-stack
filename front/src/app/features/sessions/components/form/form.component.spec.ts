import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from '../../../../services/teacher.service';
import { FormComponent } from './form.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let mockSessionApiService: any;
  let mockTeacherService: any;
  let mockRouter: any;
  let mockSnackBar: any;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };

  beforeEach(async () => {
    mockSessionApiService = {
      create: jest.fn().mockReturnValue(of({})),
      update: jest.fn().mockReturnValue(of({})),
      detail: jest.fn().mockReturnValue(
        of({
          id: 1,
          name: 'Yoga Session',
          date: new Date('2023-12-01'),
          teacher_id: 1,
          description: 'Relaxation exercises',
        })
      ),
    };

    mockTeacherService = {
      all: jest.fn().mockReturnValue(
        of([
          { id: 1, firstName: 'John', lastName: 'Doe' },
          { id: 2, firstName: 'Jane', lastName: 'Smith' },
        ])
      ),
    };

    mockRouter = {
      navigate: jest.fn(),
      url: '/sessions/create',
    };

    mockSnackBar = {
      open: jest.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule,
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },
        { provide: TeacherService, useValue: mockTeacherService },
        { provide: Router, useValue: mockRouter },
        { provide: MatSnackBar, useValue: mockSnackBar },
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { paramMap: { get: jest.fn(() => '1') } } },
        },
      ],
      declarations: [FormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form in create mode', () => {
    expect(component.onUpdate).toBeFalsy();
    expect(component.sessionForm?.value).toEqual({
      name: '',
      date: '',
      teacher_id: '',
      description: '',
    });
  });

  it('should initialize form in update mode', () => {
    mockRouter.url = '/sessions/update/1';
    component.ngOnInit();
    expect(mockSessionApiService.detail).toHaveBeenCalledWith('1');
    expect(component.onUpdate).toBeTruthy();
    expect(component.sessionForm?.value).toEqual({
      name: 'Yoga Session',
      date: '2023-12-01',
      teacher_id: 1,
      description: 'Relaxation exercises',
    });
  });

  it('should call create method on submit in create mode', () => {
    component.sessionForm?.setValue({
      name: 'Yoga Session',
      date: '2023-12-01',
      teacher_id: 1,
      description: 'Relaxation exercises',
    });

    component.submit();
    expect(mockSessionApiService.create).toHaveBeenCalledWith({
      name: 'Yoga Session',
      date: '2023-12-01',
      teacher_id: 1,
      description: 'Relaxation exercises',
    });
    expect(mockSnackBar.open).toHaveBeenCalledWith(
      'Session created !',
      'Close',
      { duration: 3000 }
    );
    expect(mockRouter.navigate).toHaveBeenCalledWith(['sessions']);
  });

  it('should call update method on submit in update mode', () => {
    mockRouter.url = '/sessions/update/1';
    component.ngOnInit();

    component.sessionForm?.setValue({
      name: 'Yoga Session Updated',
      date: '2023-12-02',
      teacher_id: 2,
      description: 'Updated description',
    });

    component.submit();
    expect(mockSessionApiService.update).toHaveBeenCalledWith('1', {
      name: 'Yoga Session Updated',
      date: '2023-12-02',
      teacher_id: 2,
      description: 'Updated description',
    });
    expect(mockSnackBar.open).toHaveBeenCalledWith(
      'Session updated !',
      'Close',
      { duration: 3000 }
    );
    expect(mockRouter.navigate).toHaveBeenCalledWith(['sessions']);
  });

  it('should fetch teachers on init', () => {
    expect(mockTeacherService.all).toHaveBeenCalled();
  });
});
