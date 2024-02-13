import { TestBed } from '@angular/core/testing';

import { EcuacionesService } from './ecuaciones.service';

describe('EcuacionesService', () => {
  let service: EcuacionesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EcuacionesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
