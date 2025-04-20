import { TestBed } from '@angular/core/testing';

import { OfferJobService } from './offer-job.service';

describe('OfferJobService', () => {
  let service: OfferJobService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OfferJobService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
