import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AgentService from './agent.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CityService from '@/entities/city/city.service';
import { type ICity } from '@/shared/model/city.model';
import { type IAgent, Agent } from '@/shared/model/agent.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AgentUpdate',
  setup() {
    const agentService = inject('agentService', () => new AgentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const agent: Ref<IAgent> = ref(new Agent());

    const cityService = inject('cityService', () => new CityService());

    const cities: Ref<ICity[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAgent = async agentId => {
      try {
        const res = await agentService().find(agentId);
        agent.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.agentId) {
      retrieveAgent(route.params.agentId);
    }

    const initRelationships = () => {
      cityService()
        .retrieve()
        .then(res => {
          cities.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      lastname: {},
      firstname: {},
      age: {},
      city: {},
      adresses: {},
    };
    const v$ = useVuelidate(validationRules, agent as any);
    v$.value.$validate();

    return {
      agentService,
      alertService,
      agent,
      previousState,
      isSaving,
      currentLanguage,
      cities,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.agent.id) {
        this.agentService()
          .update(this.agent)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.agent.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.agentService()
          .create(this.agent)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.agent.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
