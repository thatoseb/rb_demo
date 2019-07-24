import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISuspect } from 'app/shared/model/suspect.model';
import { SuspectService } from './suspect.service';

@Component({
  selector: 'jhi-suspect-delete-dialog',
  templateUrl: './suspect-delete-dialog.component.html'
})
export class SuspectDeleteDialogComponent {
  suspect: ISuspect;

  constructor(protected suspectService: SuspectService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.suspectService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'suspectListModification',
        content: 'Deleted an suspect'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-suspect-delete-popup',
  template: ''
})
export class SuspectDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ suspect }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SuspectDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.suspect = suspect;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/suspect', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/suspect', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
