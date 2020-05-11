export interface IIncidentTypes {
  id?: number;
  type?: string;
}

export class IncidentTypes implements IIncidentTypes {
  constructor(public id?: number, public type?: string) {}
}
