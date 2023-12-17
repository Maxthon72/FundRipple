import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuspicionsProjectComponent } from './suspicions-project.component';

describe('SuspicionsProjectComponent', () => {
  let component: SuspicionsProjectComponent;
  let fixture: ComponentFixture<SuspicionsProjectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuspicionsProjectComponent]
    });
    fixture = TestBed.createComponent(SuspicionsProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
