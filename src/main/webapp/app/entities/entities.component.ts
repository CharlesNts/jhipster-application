import { defineComponent, provide } from 'vue';

import CityService from './city/city.service';
import AdressService from './adress/adress.service';
import AgentService from './agent/agent.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('cityService', () => new CityService());
    provide('adressService', () => new AdressService());
    provide('agentService', () => new AgentService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
