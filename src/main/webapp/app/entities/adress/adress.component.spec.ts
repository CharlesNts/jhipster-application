/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Adress from './adress.vue';
import AdressService from './adress.service';
import AlertService from '@/shared/alert/alert.service';

type AdressComponentType = InstanceType<typeof Adress>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Adress Management Component', () => {
    let adressServiceStub: SinonStubbedInstance<AdressService>;
    let mountOptions: MountingOptions<AdressComponentType>['global'];

    beforeEach(() => {
      adressServiceStub = sinon.createStubInstance<AdressService>(AdressService);
      adressServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          adressService: () => adressServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        adressServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Adress, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(adressServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.adresses[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: AdressComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Adress, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        adressServiceStub.retrieve.reset();
        adressServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        adressServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeAdress();
        await comp.$nextTick(); // clear components

        // THEN
        expect(adressServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(adressServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
