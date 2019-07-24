import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RobocopSharedModule } from 'app/shared';
import {
  OfficerComponent,
  OfficerDetailComponent,
  OfficerUpdateComponent,
  OfficerDeletePopupComponent,
  OfficerDeleteDialogComponent,
  officerRoute,
  officerPopupRoute
} from './';

const ENTITY_STATES = [...officerRoute, ...officerPopupRoute];

@NgModule({
  imports: [RobocopSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OfficerComponent,
    OfficerDetailComponent,
    OfficerUpdateComponent,
    OfficerDeleteDialogComponent,
    OfficerDeletePopupComponent
  ],
  entryComponents: [OfficerComponent, OfficerUpdateComponent, OfficerDeleteDialogComponent, OfficerDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RobocopOfficerModule {}
