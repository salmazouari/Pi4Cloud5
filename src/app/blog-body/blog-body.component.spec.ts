import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlogBodyComponent } from './blog-body.component';

describe('BlogBodyComponent', () => {
  let component: BlogBodyComponent;
  let fixture: ComponentFixture<BlogBodyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BlogBodyComponent]
    });
    fixture = TestBed.createComponent(BlogBodyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
