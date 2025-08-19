import { TestBed } from '@angular/core/testing';

import { CritiqueService } from './critique.service';

describe('CritiqueService', () => {
  let service: CritiqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CritiqueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
