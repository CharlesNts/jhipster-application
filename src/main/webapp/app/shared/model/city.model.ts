import { type IAgent } from '@/shared/model/agent.model';

export interface ICity {
  id?: number;
  name?: string | null;
  zipCode?: string | null;
  agent?: IAgent | null;
}

export class City implements ICity {
  constructor(
    public id?: number,
    public name?: string | null,
    public zipCode?: string | null,
    public agent?: IAgent | null,
  ) {}
}
