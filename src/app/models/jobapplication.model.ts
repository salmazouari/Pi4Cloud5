export interface JobApplication {
    id?: number; // optionnel lors de la création
    studentId: number; // référence à User côté backend via @ManyToOne
    jobOfferId: number; // référence à JobOffer
    status?: string; // PENDING, ACCEPTED, REJECTED
    applicationDate?: string; // format ISO : yyyy-MM-dd
    cv?: string; // contenu ou lien vers le fichier
    motivationLetter?: string;
  }