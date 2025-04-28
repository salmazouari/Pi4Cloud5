import { Comment } from './comment'; // Ensure the import path is correct
import { User } from './user.model'; // Import the User interface

export interface BlogPost {
  id?: number;
  title: string;
  content: string;
  slug: string;
  author: User;  // Change author to be of type User
  category: { id: number; name?: string };
  createdAt?: string;  // Added createdAt field as string (ISO format date)
  updatedAt?: string;  // Added updatedAt field as string (ISO format date)
  imageUrl?: string;
  comments?: Comment[];
}
