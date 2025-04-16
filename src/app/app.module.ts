import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './interceptors/token.service';


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
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    FormsModule // Import FormsModule for template-driven forms
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
