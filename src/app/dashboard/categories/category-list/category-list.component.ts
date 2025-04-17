import { Component } from '@angular/core';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/category';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CategoryFormComponent } from '../category-form/category-form.component';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent {
  categories: Category[] = [];
  displayedColumns: string[] = ['name', 'createdAt', 'actions'];

  constructor(
    private categoryService: CategoryService,
    private router: Router,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories() {
    this.categoryService.getCategories().subscribe({
      next: (data) => this.categories = data,
      error: (err) => console.error('Error loading categories:', err)
    });
  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(CategoryFormComponent, {
      width: '400px',
      position: { 
        top: '50vh',
        left: '50vw'
      },
      panelClass: 'centered-dialog'
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) this.loadCategories();
    });
  }

  openEditDialog(category: Category) {
    const dialogRef = this.dialog.open(CategoryFormComponent, {
      width: '400px',
      position: { 
        top: '50vh',
        left: '50vw'
      },
      panelClass: 'centered-dialog',
      data: category
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) this.loadCategories();
    });
  }

  deleteCategory(id: number) {
    if(confirm('Are you sure?')) {
      this.categoryService.deleteCategory(id).subscribe({
        next: () => this.loadCategories(),
        error: (err) => console.error('Delete failed:', err)
      });
    }
  }
}