import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RobocopSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [RobocopSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [RobocopSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RobocopSharedModule {
  static forRoot() {
    return {
      ngModule: RobocopSharedModule
    };
  }
}
