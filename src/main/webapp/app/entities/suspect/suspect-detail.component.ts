import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISuspect } from 'app/shared/model/suspect.model';

@Component({
  selector: 'jhi-suspect-detail',
  templateUrl: './suspect-detail.component.html'
})
export class SuspectDetailComponent implements OnInit {
  suspect: ISuspect;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ suspect }) => {
      this.suspect = suspect;
    });
  }

  previousState() {
    window.history.back();
  }
}
