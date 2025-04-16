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


const routes: Routes = [
  { path: 'blog', component: BlogListComponent },
  { path: 'blog/:id', component: BlogSingleComponent },
  { path: 'post-blog', component: PostBlogComponent }, // Add this route
  { path: 'edit/:id', component: EditPostComponent },
  {path: 'login', component: LoginComponent},
  { path: 'acceuil', component: AcceuilComponent},
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] }, // Protect the dashboard route
  { path: '', redirectTo: '/blog', pathMatch: 'full' },
  { path: '**', redirectTo: '/blog' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
