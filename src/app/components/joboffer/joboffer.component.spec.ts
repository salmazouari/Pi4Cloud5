import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobofferComponent } from './joboffer.component';

describe('JobofferComponent', () => {
  let component: JobofferComponent;
  let fixture: ComponentFixture<JobofferComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JobofferComponent]
    });
    fixture = TestBed.createComponent(JobofferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
