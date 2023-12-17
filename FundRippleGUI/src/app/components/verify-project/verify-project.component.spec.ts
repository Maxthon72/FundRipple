import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifyProjectComponent } from './verify-project.component';

describe('VerifyProjectComponent', () => {
  let component: VerifyProjectComponent;
  let fixture: ComponentFixture<VerifyProjectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerifyProjectComponent]
    });
    fixture = TestBed.createComponent(VerifyProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
