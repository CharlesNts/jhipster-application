import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AdressService from './adress.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import AgentService from '@/entities/agent/agent.service';
import { type IAgent } from '@/shared/model/agent.model';
import { type IAdress, Adress } from '@/shared/model/adress.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AdressUpdate',
  setup() {
    const adressService = inject('adressService', () => new AdressService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const adress: Ref<IAdress> = ref(new Adress());

    const agentService = inject('agentService', () => new AgentService());

    const agents: Ref<IAgent[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAdress = async adressId => {
      try {
        const res = await adressService().find(adressId);
        adress.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.adressId) {
      retrieveAdress(route.params.adressId);
    }

    const initRelationships = () => {
      agentService()
        .retrieve()
        .then(res => {
          agents.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      streetNumb: {},
      streetName: {},
      agent: {},
    };
    const v$ = useVuelidate(validationRules, adress as any);
    v$.value.$validate();

    return {
      adressService,
      alertService,
      adress,
      previousState,
      isSaving,
      currentLanguage,
      agents,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.adress.id) {
        this.adressService()
          .update(this.adress)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.adress.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.adressService()
          .create(this.adress)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.adress.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
