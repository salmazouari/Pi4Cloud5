import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobApplicationSubmitComponent } from './job-application-submit.component';

describe('JobApplicationSubmitComponent', () => {
  let component: JobApplicationSubmitComponent;
  let fixture: ComponentFixture<JobApplicationSubmitComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JobApplicationSubmitComponent]
    });
    fixture = TestBed.createComponent(JobApplicationSubmitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
