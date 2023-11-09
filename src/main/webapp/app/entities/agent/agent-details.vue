<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="agent">
        <h2 class="jh-entity-heading" data-cy="agentDetailsHeading">
          <span v-text="t$('jhipsterApp.agent.detail.title')"></span> {{ agent.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.agent.lastname')"></span>
          </dt>
          <dd>
            <span>{{ agent.lastname }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.agent.firstname')"></span>
          </dt>
          <dd>
            <span>{{ agent.firstname }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.agent.age')"></span>
          </dt>
          <dd>
            <span>{{ agent.age }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.agent.city')"></span>
          </dt>
          <dd>
            <div v-if="agent.city">
              <router-link :to="{ name: 'CityView', params: { cityId: agent.city.id } }">{{ agent.city.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="agent.id" :to="{ name: 'AgentEdit', params: { agentId: agent.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./agent-details.component.ts"></script>
