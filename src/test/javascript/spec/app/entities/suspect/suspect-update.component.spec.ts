/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { RobocopTestModule } from '../../../test.module';
import { SuspectUpdateComponent } from 'app/entities/suspect/suspect-update.component';
import { SuspectService } from 'app/entities/suspect/suspect.service';
import { Suspect } from 'app/shared/model/suspect.model';

describe('Component Tests', () => {
  describe('Suspect Management Update Component', () => {
    let comp: SuspectUpdateComponent;
    let fixture: ComponentFixture<SuspectUpdateComponent>;
    let service: SuspectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RobocopTestModule],
        declarations: [SuspectUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SuspectUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SuspectUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SuspectService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Suspect(123);
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
        const entity = new Suspect();
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
