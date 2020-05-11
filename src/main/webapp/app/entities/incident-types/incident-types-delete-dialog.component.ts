import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncidentTypes } from 'app/shared/model/incident-types.model';
import { IncidentTypesService } from './incident-types.service';

@Component({
  selector: 'jhi-incident-types-delete-dialog',
  templateUrl: './incident-types-delete-dialog.component.html'
})
export class IncidentTypesDeleteDialogComponent {
  incidentTypes: IIncidentTypes;

  constructor(
    protected incidentTypesService: IncidentTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.incidentTypesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'incidentTypesListModification',
        content: 'Deleted an incidentTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-incident-types-delete-popup',
  template: ''
})
export class IncidentTypesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ incidentTypes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(IncidentTypesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.incidentTypes = incidentTypes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/incident-types', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/incident-types', { outlets: { popup: null } }]);
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
