import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AdressService from './adress.service';
import { type IAdress } from '@/shared/model/adress.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AdressDetails',
  setup() {
    const adressService = inject('adressService', () => new AdressService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const adress: Ref<IAdress> = ref({});

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

    return {
      alertService,
      adress,

      previousState,
      t$: useI18n().t,
    };
  },
});
