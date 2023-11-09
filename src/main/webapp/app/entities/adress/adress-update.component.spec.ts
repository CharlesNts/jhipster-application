/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AdressUpdate from './adress-update.vue';
import AdressService from './adress.service';
import AlertService from '@/shared/alert/alert.service';

import AgentService from '@/entities/agent/agent.service';

type AdressUpdateComponentType = InstanceType<typeof AdressUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const adressSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AdressUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Adress Management Update Component', () => {
    let comp: AdressUpdateComponentType;
    let adressServiceStub: SinonStubbedInstance<AdressService>;

    beforeEach(() => {
      route = {};
      adressServiceStub = sinon.createStubInstance<AdressService>(AdressService);
      adressServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          adressService: () => adressServiceStub,
          agentService: () =>
            sinon.createStubInstance<AgentService>(AgentService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(AdressUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.adress = adressSample;
        adressServiceStub.update.resolves(adressSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adressServiceStub.update.calledWith(adressSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        adressServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AdressUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.adress = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adressServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        adressServiceStub.find.resolves(adressSample);
        adressServiceStub.retrieve.resolves([adressSample]);

        // WHEN
        route = {
          params: {
            adressId: '' + adressSample.id,
          },
        };
        const wrapper = shallowMount(AdressUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.adress).toMatchObject(adressSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        adressServiceStub.find.resolves(adressSample);
        const wrapper = shallowMount(AdressUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
