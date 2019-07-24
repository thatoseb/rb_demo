import { IIncident } from 'app/shared/model/incident.model';

export interface ISuspect {
  id?: number;
  firstName?: string;
  lastName?: string;
  lastKnownAddress?: string;
  linkedIncidents?: IIncident[];
}

export class Suspect implements ISuspect {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public lastKnownAddress?: string,
    public linkedIncidents?: IIncident[]
  ) {}
}
