import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OeuvresComponent } from './oeuvres.component';

describe('OeuvresComponent', () => {
  let component: OeuvresComponent;
  let fixture: ComponentFixture<OeuvresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OeuvresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OeuvresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
