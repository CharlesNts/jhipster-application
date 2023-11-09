import { type ICity } from '@/shared/model/city.model';
import { type IAdress } from '@/shared/model/adress.model';

export interface IAgent {
  id?: number;
  lastname?: string | null;
  firstname?: string | null;
  age?: number | null;
  city?: ICity | null;
  adresses?: IAdress[] | null;
}

export class Agent implements IAgent {
  constructor(
    public id?: number,
    public lastname?: string | null,
    public firstname?: string | null,
    public age?: number | null,
    public city?: ICity | null,
    public adresses?: IAdress[] | null,
  ) {}
}
