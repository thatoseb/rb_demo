import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IIncidentTypes, IncidentTypes } from 'app/shared/model/incident-types.model';
import { IncidentTypesService } from './incident-types.service';

@Component({
  selector: 'jhi-incident-types-update',
  templateUrl: './incident-types-update.component.html'
})
export class IncidentTypesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]]
  });

  constructor(protected incidentTypesService: IncidentTypesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ incidentTypes }) => {
      this.updateForm(incidentTypes);
    });
  }

  updateForm(incidentTypes: IIncidentTypes) {
    this.editForm.patchValue({
      id: incidentTypes.id,
      type: incidentTypes.type
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const incidentTypes = this.createFromForm();
    if (incidentTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.incidentTypesService.update(incidentTypes));
    } else {
      this.subscribeToSaveResponse(this.incidentTypesService.create(incidentTypes));
    }
  }

  private createFromForm(): IIncidentTypes {
    return {
      ...new IncidentTypes(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncidentTypes>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
