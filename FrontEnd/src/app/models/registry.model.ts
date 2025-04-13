import { Seat } from "./seats.model";
export interface Registration {
  id?: number;
  name: string;
  lastname: string;
  email: string;
  school?: string;
  user?: {
    id: number;
  };

  // Add other properties as needed
}