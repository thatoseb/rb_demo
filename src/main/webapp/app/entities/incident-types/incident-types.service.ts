import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIncidentTypes } from 'app/shared/model/incident-types.model';

type EntityResponseType = HttpResponse<IIncidentTypes>;
type EntityArrayResponseType = HttpResponse<IIncidentTypes[]>;

@Injectable({ providedIn: 'root' })
export class IncidentTypesService {
  public resourceUrl = SERVER_API_URL + 'api/incident-types';

  constructor(protected http: HttpClient) {}

  create(incidentTypes: IIncidentTypes): Observable<EntityResponseType> {
    return this.http.post<IIncidentTypes>(this.resourceUrl, incidentTypes, { observe: 'response' });
  }

  update(incidentTypes: IIncidentTypes): Observable<EntityResponseType> {
    return this.http.put<IIncidentTypes>(this.resourceUrl, incidentTypes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIncidentTypes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIncidentTypes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
