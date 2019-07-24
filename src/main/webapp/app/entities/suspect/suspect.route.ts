import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Suspect } from 'app/shared/model/suspect.model';
import { SuspectService } from './suspect.service';
import { SuspectComponent } from './suspect.component';
import { SuspectDetailComponent } from './suspect-detail.component';
import { SuspectUpdateComponent } from './suspect-update.component';
import { SuspectDeletePopupComponent } from './suspect-delete-dialog.component';
import { ISuspect } from 'app/shared/model/suspect.model';

@Injectable({ providedIn: 'root' })
export class SuspectResolve implements Resolve<ISuspect> {
  constructor(private service: SuspectService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISuspect> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Suspect>) => response.ok),
        map((suspect: HttpResponse<Suspect>) => suspect.body)
      );
    }
    return of(new Suspect());
  }
}

export const suspectRoute: Routes = [
  {
    path: '',
    component: SuspectComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Suspects'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SuspectDetailComponent,
    resolve: {
      suspect: SuspectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Suspects'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SuspectUpdateComponent,
    resolve: {
      suspect: SuspectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Suspects'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SuspectUpdateComponent,
    resolve: {
      suspect: SuspectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Suspects'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const suspectPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SuspectDeletePopupComponent,
    resolve: {
      suspect: SuspectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Suspects'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
