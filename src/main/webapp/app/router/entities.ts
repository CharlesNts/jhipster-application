import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const City = () => import('@/entities/city/city.vue');
const CityUpdate = () => import('@/entities/city/city-update.vue');
const CityDetails = () => import('@/entities/city/city-details.vue');

const Adress = () => import('@/entities/adress/adress.vue');
const AdressUpdate = () => import('@/entities/adress/adress-update.vue');
const AdressDetails = () => import('@/entities/adress/adress-details.vue');

const Agent = () => import('@/entities/agent/agent.vue');
const AgentUpdate = () => import('@/entities/agent/agent-update.vue');
const AgentDetails = () => import('@/entities/agent/agent-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'city',
      name: 'City',
      component: City,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'city/new',
      name: 'CityCreate',
      component: CityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'city/:cityId/edit',
      name: 'CityEdit',
      component: CityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'city/:cityId/view',
      name: 'CityView',
      component: CityDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adress',
      name: 'Adress',
      component: Adress,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adress/new',
      name: 'AdressCreate',
      component: AdressUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adress/:adressId/edit',
      name: 'AdressEdit',
      component: AdressUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adress/:adressId/view',
      name: 'AdressView',
      component: AdressDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'agent',
      name: 'Agent',
      component: Agent,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'agent/new',
      name: 'AgentCreate',
      component: AgentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'agent/:agentId/edit',
      name: 'AgentEdit',
      component: AgentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'agent/:agentId/view',
      name: 'AgentView',
      component: AgentDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
