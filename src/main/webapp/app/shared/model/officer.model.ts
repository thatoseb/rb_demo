import { IIncident } from 'app/shared/model/incident.model';

export interface IOfficer {
  id?: number;
  firstName?: string;
  lastName?: string;
  identificationNumber?: string;
  designation?: string;
  assignedIncidents?: IIncident[];
}

export class Officer implements IOfficer {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public identificationNumber?: string,
    public designation?: string,
    public assignedIncidents?: IIncident[]
  ) {}
}
