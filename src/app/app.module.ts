import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './interceptors/token.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatExpansionModule } from '@angular/material/expansion';



import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { BodyComponent } from './body/body.component';
import { FooterComponent } from './footer/footer.component';
import { BlogBodyComponent } from './blog-body/blog-body.component';
import { EventsComponent } from './events/events.component';
import { BlogListComponent } from './blog-list/blog-list.component';
import { BlogSingleComponent } from './blog-single/blog-single.component';
import { CommentsSectionComponent } from './comments-section/comments-section.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { PostBlogComponent } from './post-blog/post-blog.component';
import { EditPostComponent } from './edit-post/edit-post.component';
import { AcceuilComponent } from './acceuil/acceuil.component';
import { LoginComponent } from './auth/login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CategoryListComponent } from './dashboard/categories/category-list/category-list.component';
import { CategoryFormComponent } from './dashboard/categories/category-form/category-form.component';
import {MatTableModule} from "@angular/material/table";
import {MatDialogModule} from "@angular/material/dialog";
import { BlogPostListComponent } from './dashboard/blog-post-list/blog-post-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BodyComponent,
    FooterComponent,
    BlogBodyComponent,
    EventsComponent,
    BlogListComponent,
    BlogSingleComponent,
    CommentsSectionComponent,
    SidebarComponent,
    PostBlogComponent,
    EditPostComponent,
    AcceuilComponent,
    LoginComponent,
    DashboardComponent,
    CategoryListComponent,
    CategoryFormComponent,
    BlogPostListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    FormsModule, // Import FormsModule for template-driven forms
    BrowserAnimationsModule, // Nécessaire pour Angular Material
    // Modules Angular Material
    MatIconModule,
    MatCardModule,
    MatListModule,
    MatButtonModule,
    MatInputModule,
    MatTableModule,
    CommonModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatDialogModule

  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
