import { Moment } from 'moment';
import { IOfficer } from 'app/shared/model/officer.model';
import { ISuspect } from 'app/shared/model/suspect.model';

export const enum IncidentStatus {
  OPEN = 'OPEN',
  CLOSED = 'CLOSED'
}

export interface IIncident {
  id?: number;
  startDate?: Moment;
  incidentStatus?: IncidentStatus;
  location?: string;
  description?: string;
  officers?: IOfficer[];
  suspects?: ISuspect[];
  userLogin?: string;
  userId?: number;
}

export class Incident implements IIncident {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public incidentStatus?: IncidentStatus,
    public location?: string,
    public description?: string,
    public officers?: IOfficer[],
    public suspects?: ISuspect[],
    public userLogin?: string,
    public userId?: number
  ) {}
}
