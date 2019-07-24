import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RobocopSharedModule } from 'app/shared';
import {
  SuspectComponent,
  SuspectDetailComponent,
  SuspectUpdateComponent,
  SuspectDeletePopupComponent,
  SuspectDeleteDialogComponent,
  suspectRoute,
  suspectPopupRoute
} from './';

const ENTITY_STATES = [...suspectRoute, ...suspectPopupRoute];

@NgModule({
  imports: [RobocopSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SuspectComponent,
    SuspectDetailComponent,
    SuspectUpdateComponent,
    SuspectDeleteDialogComponent,
    SuspectDeletePopupComponent
  ],
  entryComponents: [SuspectComponent, SuspectUpdateComponent, SuspectDeleteDialogComponent, SuspectDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RobocopSuspectModule {}
