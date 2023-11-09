<template>
  <div>
    <h2 id="page-heading" data-cy="AgentHeading">
      <span v-text="t$('jhipsterApp.agent.home.title')" id="agent-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.agent.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'AgentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-agent"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.agent.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && agents && agents.length === 0">
      <span v-text="t$('jhipsterApp.agent.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="agents && agents.length > 0">
      <table class="table table-striped" aria-describedby="agents">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.agent.lastname')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.agent.firstname')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.agent.age')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.agent.city')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="agent in agents" :key="agent.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AgentView', params: { agentId: agent.id } }">{{ agent.id }}</router-link>
            </td>
            <td>{{ agent.lastname }}</td>
            <td>{{ agent.firstname }}</td>
            <td>{{ agent.age }}</td>
            <td>
              <div v-if="agent.city">
                <router-link :to="{ name: 'CityView', params: { cityId: agent.city.id } }">{{ agent.city.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AgentView', params: { agentId: agent.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AgentEdit', params: { agentId: agent.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(agent)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="jhipsterApp.agent.delete.question" data-cy="agentDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-agent-heading" v-text="t$('jhipsterApp.agent.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-agent"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeAgent()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./agent.component.ts"></script>
