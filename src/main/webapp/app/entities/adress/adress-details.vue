<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="adress">
        <h2 class="jh-entity-heading" data-cy="adressDetailsHeading">
          <span v-text="t$('jhipsterApp.adress.detail.title')"></span> {{ adress.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.adress.streetNumb')"></span>
          </dt>
          <dd>
            <span>{{ adress.streetNumb }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.adress.streetName')"></span>
          </dt>
          <dd>
            <span>{{ adress.streetName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.adress.agent')"></span>
          </dt>
          <dd>
            <div v-if="adress.agent">
              <router-link :to="{ name: 'AgentView', params: { agentId: adress.agent.id } }">{{ adress.agent.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="adress.id" :to="{ name: 'AdressEdit', params: { adressId: adress.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./adress-details.component.ts"></script>
