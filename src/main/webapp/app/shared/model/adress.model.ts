import { type IAgent } from '@/shared/model/agent.model';

export interface IAdress {
  id?: number;
  streetNumb?: string | null;
  streetName?: string | null;
  agent?: IAgent | null;
}

export class Adress implements IAdress {
  constructor(
    public id?: number,
    public streetNumb?: string | null,
    public streetName?: string | null,
    public agent?: IAgent | null,
  ) {}
}
