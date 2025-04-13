import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SeatService } from './seat.service';

describe('SeatService', () => {
  let service: SeatService;
  let httpMock: HttpTestingController;
  const apiUrl = 'http://localhost:8093/seats';  // Same as in the service

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SeatService],
    });
    service = TestBed.inject(SeatService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create or update a seat', () => {
    const mockSeat = { id: 1, placement: 'A1', type: 'VIP' };  // Added 'id'

    service.createOrUpdateSeat(mockSeat).subscribe((response) => {
      expect(response).toEqual(mockSeat);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');
    req.flush(mockSeat);
  });

  it('should delete a seat', () => {
    const placement = 'A1';

    service.deleteSeat(placement).subscribe((response) => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne(`${apiUrl}/${placement}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('should get all seats', () => {
    const mockSeats = [
      { id: 1, placement: 'A1', type: 'VIP' },  // Added 'id'
      { id: 2, placement: 'B2', type: 'Standard' },  // Added 'id'
    ];

    service.getAllSeats().subscribe((seats) => {
      expect(seats.length).toBe(2);
      expect(seats).toEqual(mockSeats);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockSeats);
  });
});
