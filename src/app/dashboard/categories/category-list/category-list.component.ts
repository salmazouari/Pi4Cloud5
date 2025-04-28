import { Component } from '@angular/core';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/category';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CategoryFormComponent } from '../category-form/category-form.component';
import { HttpClient } from '@angular/common/http';
import { ChartConfiguration, ChartType } from 'chart.js';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent {
  categories: Category[] = [];
  displayedColumns: string[] = ['name', 'createdAt', 'actions'];
  // Chart Data
  public barChartLabels: string[] = [];
  public barChartData: number[] = [];
  public barChartType: 'bar' = 'bar';

  constructor(
    private categoryService: CategoryService,
    private router: Router,
    private dialog: MatDialog,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.loadCategories();
    this.loadPostStats(); // 🚀 Load chart data
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

  loadPostStats() {
    this.http.get<any[]>('http://localhost:8080/api/blog-posts/stats/posts-by-category')
      .subscribe({
        next: (data) => {
          this.barChartLabels = data.map(item => item.category);
          this.barChartData = data.map(item => item.count);
        },
        error: (err) => console.error('Error loading post stats:', err)
      });
  }


  // Optional chart options
  public barChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    plugins: {
      legend: { display: false },
      title: {
        display: true,
        text: 'Posts by Category'
      }
    }
  };


}
