import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOfficer, Officer } from 'app/shared/model/officer.model';
import { OfficerService } from './officer.service';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from 'app/entities/incident';

@Component({
  selector: 'jhi-officer-update',
  templateUrl: './officer-update.component.html'
})
export class OfficerUpdateComponent implements OnInit {
  isSaving: boolean;

  incidents: IIncident[];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    identificationNumber: [],
    designation: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected officerService: OfficerService,
    protected incidentService: IncidentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ officer }) => {
      this.updateForm(officer);
    });
    this.incidentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIncident[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIncident[]>) => response.body)
      )
      .subscribe((res: IIncident[]) => (this.incidents = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(officer: IOfficer) {
    this.editForm.patchValue({
      id: officer.id,
      firstName: officer.firstName,
      lastName: officer.lastName,
      identificationNumber: officer.identificationNumber,
      designation: officer.designation
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const officer = this.createFromForm();
    if (officer.id !== undefined) {
      this.subscribeToSaveResponse(this.officerService.update(officer));
    } else {
      this.subscribeToSaveResponse(this.officerService.create(officer));
    }
  }

  private createFromForm(): IOfficer {
    return {
      ...new Officer(),
      id: this.editForm.get(['id']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      identificationNumber: this.editForm.get(['identificationNumber']).value,
      designation: this.editForm.get(['designation']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOfficer>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackIncidentById(index: number, item: IIncident) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
