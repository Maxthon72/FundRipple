import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecificProjectComponent } from './specific-project.component';

describe('SpecificProjectComponent', () => {
  let component: SpecificProjectComponent;
  let fixture: ComponentFixture<SpecificProjectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SpecificProjectComponent]
    });
    fixture = TestBed.createComponent(SpecificProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
