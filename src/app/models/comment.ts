import { User } from './user.model';  // Import the User interface

export interface Comment {
  id?: number;
  content: string;
  createdAt?: Date;  // For the creation timestamp
  updatedAt?: Date;  // For the updated timestamp
  deletedAt?: Date;  // For the deleted timestamp (if applicable)
  post: { id: number };  // Reference to the blog post the comment belongs to
  user: {
    username: string;
    imageUrl: string;
    userId: number };  // Only userId here, not the full User object
}
