/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RobocopTestModule } from '../../../test.module';
import { SuspectDeleteDialogComponent } from 'app/entities/suspect/suspect-delete-dialog.component';
import { SuspectService } from 'app/entities/suspect/suspect.service';

describe('Component Tests', () => {
  describe('Suspect Management Delete Component', () => {
    let comp: SuspectDeleteDialogComponent;
    let fixture: ComponentFixture<SuspectDeleteDialogComponent>;
    let service: SuspectService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RobocopTestModule],
        declarations: [SuspectDeleteDialogComponent]
      })
        .overrideTemplate(SuspectDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SuspectDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SuspectService);
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
