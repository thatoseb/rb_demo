import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISuspect } from 'app/shared/model/suspect.model';

type EntityResponseType = HttpResponse<ISuspect>;
type EntityArrayResponseType = HttpResponse<ISuspect[]>;

@Injectable({ providedIn: 'root' })
export class SuspectService {
  public resourceUrl = SERVER_API_URL + 'api/suspects';

  constructor(protected http: HttpClient) {}

  create(suspect: ISuspect): Observable<EntityResponseType> {
    return this.http.post<ISuspect>(this.resourceUrl, suspect, { observe: 'response' });
  }

  update(suspect: ISuspect): Observable<EntityResponseType> {
    return this.http.put<ISuspect>(this.resourceUrl, suspect, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISuspect>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISuspect[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
