import { User } from './user.model';

export interface JobOffer {
  id?: number;
  title: string;
  description: string;
  location: string;
  region: string;
  type: string; // e.g., Full-time, Part-time
  email: string;
  image: string;
  recruiter: User;

 
}