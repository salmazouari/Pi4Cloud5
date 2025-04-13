// seats.model.ts
export interface Seat {
  id?: number;
  placement: string;
  event: { EventID: number };  // Change from 'id' to 'EventID'
  isBooked: boolean;
}