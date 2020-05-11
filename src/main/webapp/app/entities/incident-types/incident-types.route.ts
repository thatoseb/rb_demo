import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IncidentTypes } from 'app/shared/model/incident-types.model';
import { IncidentTypesService } from './incident-types.service';
import { IncidentTypesComponent } from './incident-types.component';
import { IncidentTypesDetailComponent } from './incident-types-detail.component';
import { IncidentTypesUpdateComponent } from './incident-types-update.component';
import { IncidentTypesDeletePopupComponent } from './incident-types-delete-dialog.component';
import { IIncidentTypes } from 'app/shared/model/incident-types.model';

@Injectable({ providedIn: 'root' })
export class IncidentTypesResolve implements Resolve<IIncidentTypes> {
  constructor(private service: IncidentTypesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIncidentTypes> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<IncidentTypes>) => response.ok),
        map((incidentTypes: HttpResponse<IncidentTypes>) => incidentTypes.body)
      );
    }
    return of(new IncidentTypes());
  }
}

export const incidentTypesRoute: Routes = [
  {
    path: '',
    component: IncidentTypesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'IncidentTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IncidentTypesDetailComponent,
    resolve: {
      incidentTypes: IncidentTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IncidentTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IncidentTypesUpdateComponent,
    resolve: {
      incidentTypes: IncidentTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IncidentTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IncidentTypesUpdateComponent,
    resolve: {
      incidentTypes: IncidentTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IncidentTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const incidentTypesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: IncidentTypesDeletePopupComponent,
    resolve: {
      incidentTypes: IncidentTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IncidentTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
