import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BlogListComponent } from './blog-list/blog-list.component';
import { BlogSingleComponent } from './blog-single/blog-single.component';
import { PostBlogComponent } from './post-blog/post-blog.component'; // Import the new component
import { EditPostComponent } from './edit-post/edit-post.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './auth/login/login.component';
import { AcceuilComponent } from './acceuil/acceuil.component';
import { AuthGuard } from './auth/auth.guard';
import {CategoryListComponent} from "./dashboard/categories/category-list/category-list.component";
import {CategoryFormComponent} from "./dashboard/categories/category-form/category-form.component";
import { BlogPostListComponent } from './dashboard/blog-post-list/blog-post-list.component';


const routes: Routes = [
  { path: 'blog', component: BlogListComponent },
  { path: 'blog/:id', component: BlogSingleComponent },
  { path: 'post-blog', component: PostBlogComponent }, // Add this route
  { path: 'edit/:id', component: EditPostComponent },
  {path: 'login', component: LoginComponent},
  { path: 'acceuil', component: AcceuilComponent},
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'categories', component: CategoryListComponent },
      { path: 'categories/new', component: CategoryFormComponent },
      { path: 'categories/edit/:id', component: CategoryFormComponent },
      { path: 'posts', component: BlogPostListComponent },

    ]
  },
  { path: '', redirectTo: '/blog', pathMatch: 'full' },
  { path: '**', redirectTo: '/blog' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
