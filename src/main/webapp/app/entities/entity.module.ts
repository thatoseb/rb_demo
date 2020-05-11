import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'incident',
        loadChildren: './incident/incident.module#RobocopIncidentModule'
      },
      {
        path: 'officer',
        loadChildren: './officer/officer.module#RobocopOfficerModule'
      },
      {
        path: 'suspect',
        loadChildren: './suspect/suspect.module#RobocopSuspectModule'
      },
      {
        path: 'incident-types',
        loadChildren: './incident-types/incident-types.module#RobocopIncidentTypesModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RobocopEntityModule {}
