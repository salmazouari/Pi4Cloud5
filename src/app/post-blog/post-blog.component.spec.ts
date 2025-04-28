import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostBlogComponent } from './post-blog.component';

describe('PostBlogComponent', () => {
  let component: PostBlogComponent;
  let fixture: ComponentFixture<PostBlogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostBlogComponent]
    });
    fixture = TestBed.createComponent(PostBlogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
