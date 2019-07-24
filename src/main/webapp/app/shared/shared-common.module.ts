import { NgModule } from '@angular/core';

import { RobocopSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [RobocopSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [RobocopSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class RobocopSharedCommonModule {}
