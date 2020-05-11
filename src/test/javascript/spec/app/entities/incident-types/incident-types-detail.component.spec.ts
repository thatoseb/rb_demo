/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RobocopTestModule } from '../../../test.module';
import { IncidentTypesDetailComponent } from 'app/entities/incident-types/incident-types-detail.component';
import { IncidentTypes } from 'app/shared/model/incident-types.model';

describe('Component Tests', () => {
  describe('IncidentTypes Management Detail Component', () => {
    let comp: IncidentTypesDetailComponent;
    let fixture: ComponentFixture<IncidentTypesDetailComponent>;
    const route = ({ data: of({ incidentTypes: new IncidentTypes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RobocopTestModule],
        declarations: [IncidentTypesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IncidentTypesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncidentTypesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incidentTypes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
