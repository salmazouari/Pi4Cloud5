import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/category';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent {
  form: FormGroup;
  isEdit = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private dialogRef: MatDialogRef<CategoryFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Category
  ) {
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(50)]]
    });

    if(data) {
      this.isEdit = true;
      this.form.patchValue(data);
    }
  }

  onSubmit() {
    if(this.form.valid) {
      const operation = this.isEdit
        ? this.categoryService.updateCategory(this.data.id, this.form.value)
        : this.categoryService.createCategory(this.form.value);

      operation.subscribe({
        next: () => this.dialogRef.close(true),
        error: (err) => console.error('Operation failed:', err)
      });
    }
  }
}
