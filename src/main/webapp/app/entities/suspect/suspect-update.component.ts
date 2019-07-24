import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISuspect, Suspect } from 'app/shared/model/suspect.model';
import { SuspectService } from './suspect.service';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from 'app/entities/incident';

@Component({
  selector: 'jhi-suspect-update',
  templateUrl: './suspect-update.component.html'
})
export class SuspectUpdateComponent implements OnInit {
  isSaving: boolean;

  incidents: IIncident[];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    lastKnownAddress: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected suspectService: SuspectService,
    protected incidentService: IncidentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ suspect }) => {
      this.updateForm(suspect);
    });
    this.incidentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIncident[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIncident[]>) => response.body)
      )
      .subscribe((res: IIncident[]) => (this.incidents = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(suspect: ISuspect) {
    this.editForm.patchValue({
      id: suspect.id,
      firstName: suspect.firstName,
      lastName: suspect.lastName,
      lastKnownAddress: suspect.lastKnownAddress
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const suspect = this.createFromForm();
    if (suspect.id !== undefined) {
      this.subscribeToSaveResponse(this.suspectService.update(suspect));
    } else {
      this.subscribeToSaveResponse(this.suspectService.create(suspect));
    }
  }

  private createFromForm(): ISuspect {
    return {
      ...new Suspect(),
      id: this.editForm.get(['id']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      lastKnownAddress: this.editForm.get(['lastKnownAddress']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuspect>>) {
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
