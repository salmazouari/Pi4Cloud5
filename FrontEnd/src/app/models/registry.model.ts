// registry.model.ts
export interface Registration {
    id?: number;
    name: string;
    lastname: string;
    email: string;
    school?: string;
    seats: { id: number }[]; // Only needs ID for backend
    totalPrice: number;
    registrationDate?: string;
    user1?: number;
  }
