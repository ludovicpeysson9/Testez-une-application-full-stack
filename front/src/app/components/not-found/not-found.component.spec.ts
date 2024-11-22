import { ComponentFixture, TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { NotFoundComponent } from './not-found.component';

describe('NotFoundComponent', () => {
  let component: NotFoundComponent;
  let fixture: ComponentFixture<NotFoundComponent>;
  let compiled: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotFoundComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NotFoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    compiled = fixture.nativeElement as HTMLElement;
  });

  // Vérifie que le composant est créé
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Vérifie que le message "Page not found !" est affiché
  it('should display the correct message', () => {
    const heading = compiled.querySelector('h1');
    expect(heading?.textContent).toBe('Page not found !');
  });

  // Vérifie que la classe CSS "flex justify-center mt3" est présente
  it('should have correct CSS classes applied', () => {
    const container = compiled.querySelector('div');
    expect(container?.classList.contains('flex')).toBeTruthy();
    expect(container?.classList.contains('justify-center')).toBeTruthy();
    expect(container?.classList.contains('mt3')).toBeTruthy();
  });
});
