import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RobocopSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';

@NgModule({
  imports: [RobocopSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RobocopHomeModule {}
