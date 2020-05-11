import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncidentTypes } from 'app/shared/model/incident-types.model';

@Component({
  selector: 'jhi-incident-types-detail',
  templateUrl: './incident-types-detail.component.html'
})
export class IncidentTypesDetailComponent implements OnInit {
  incidentTypes: IIncidentTypes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ incidentTypes }) => {
      this.incidentTypes = incidentTypes;
    });
  }

  previousState() {
    window.history.back();
  }
}
