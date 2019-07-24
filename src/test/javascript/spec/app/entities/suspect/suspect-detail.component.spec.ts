/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RobocopTestModule } from '../../../test.module';
import { SuspectDetailComponent } from 'app/entities/suspect/suspect-detail.component';
import { Suspect } from 'app/shared/model/suspect.model';

describe('Component Tests', () => {
  describe('Suspect Management Detail Component', () => {
    let comp: SuspectDetailComponent;
    let fixture: ComponentFixture<SuspectDetailComponent>;
    const route = ({ data: of({ suspect: new Suspect(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RobocopTestModule],
        declarations: [SuspectDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SuspectDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SuspectDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.suspect).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
