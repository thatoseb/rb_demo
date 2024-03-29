/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RobocopTestModule } from '../../../test.module';
import { IncidentDeleteDialogComponent } from 'app/entities/incident/incident-delete-dialog.component';
import { IncidentService } from 'app/entities/incident/incident.service';

describe('Component Tests', () => {
  describe('Incident Management Delete Component', () => {
    let comp: IncidentDeleteDialogComponent;
    let fixture: ComponentFixture<IncidentDeleteDialogComponent>;
    let service: IncidentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RobocopTestModule],
        declarations: [IncidentDeleteDialogComponent]
      })
        .overrideTemplate(IncidentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncidentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncidentService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
