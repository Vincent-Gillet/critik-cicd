import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarteOeuvreComponent } from './carte-oeuvre.component';

describe('CarteOeuvreComponent', () => {
  let component: CarteOeuvreComponent;
  let fixture: ComponentFixture<CarteOeuvreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarteOeuvreComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarteOeuvreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
