import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IIncident, Incident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';
import { IOfficer } from 'app/shared/model/officer.model';
import { OfficerService } from 'app/entities/officer';
import { ISuspect } from 'app/shared/model/suspect.model';
import { SuspectService } from 'app/entities/suspect';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-incident-update',
  templateUrl: './incident-update.component.html'
})
export class IncidentUpdateComponent implements OnInit {
  isSaving: boolean;

  officers: IOfficer[];

  suspects: ISuspect[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    startDate: [],
    incidentStatus: [],
    location: [],
    description: [],
    officers: [],
    suspects: [],
    userId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected incidentService: IncidentService,
    protected officerService: OfficerService,
    protected suspectService: SuspectService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ incident }) => {
      this.updateForm(incident);
    });
    this.officerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOfficer[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOfficer[]>) => response.body)
      )
      .subscribe((res: IOfficer[]) => (this.officers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.suspectService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISuspect[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISuspect[]>) => response.body)
      )
      .subscribe((res: ISuspect[]) => (this.suspects = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(incident: IIncident) {
    this.editForm.patchValue({
      id: incident.id,
      startDate: incident.startDate != null ? incident.startDate.format(DATE_TIME_FORMAT) : null,
      incidentStatus: incident.incidentStatus,
      location: incident.location,
      description: incident.description,
      officers: incident.officers,
      suspects: incident.suspects,
      userId: incident.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const incident = this.createFromForm();
    if (incident.id !== undefined) {
      this.subscribeToSaveResponse(this.incidentService.update(incident));
    } else {
      this.subscribeToSaveResponse(this.incidentService.create(incident));
    }
  }

  private createFromForm(): IIncident {
    return {
      ...new Incident(),
      id: this.editForm.get(['id']).value,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      incidentStatus: this.editForm.get(['incidentStatus']).value,
      location: this.editForm.get(['location']).value,
      description: this.editForm.get(['description']).value,
      officers: this.editForm.get(['officers']).value,
      suspects: this.editForm.get(['suspects']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncident>>) {
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

  trackOfficerById(index: number, item: IOfficer) {
    return item.id;
  }

  trackSuspectById(index: number, item: ISuspect) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
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
