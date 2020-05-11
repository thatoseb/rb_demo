import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RobocopSharedModule } from 'app/shared';
import {
  IncidentTypesComponent,
  IncidentTypesDetailComponent,
  IncidentTypesUpdateComponent,
  IncidentTypesDeletePopupComponent,
  IncidentTypesDeleteDialogComponent,
  incidentTypesRoute,
  incidentTypesPopupRoute
} from './';

const ENTITY_STATES = [...incidentTypesRoute, ...incidentTypesPopupRoute];

@NgModule({
  imports: [RobocopSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    IncidentTypesComponent,
    IncidentTypesDetailComponent,
    IncidentTypesUpdateComponent,
    IncidentTypesDeleteDialogComponent,
    IncidentTypesDeletePopupComponent
  ],
  entryComponents: [
    IncidentTypesComponent,
    IncidentTypesUpdateComponent,
    IncidentTypesDeleteDialogComponent,
    IncidentTypesDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RobocopIncidentTypesModule {}
