/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { RobocopTestModule } from '../../../test.module';
import { IncidentTypesUpdateComponent } from 'app/entities/incident-types/incident-types-update.component';
import { IncidentTypesService } from 'app/entities/incident-types/incident-types.service';
import { IncidentTypes } from 'app/shared/model/incident-types.model';

describe('Component Tests', () => {
  describe('IncidentTypes Management Update Component', () => {
    let comp: IncidentTypesUpdateComponent;
    let fixture: ComponentFixture<IncidentTypesUpdateComponent>;
    let service: IncidentTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RobocopTestModule],
        declarations: [IncidentTypesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IncidentTypesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncidentTypesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncidentTypesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IncidentTypes(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new IncidentTypes();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
