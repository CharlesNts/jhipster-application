<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.agent.home.createOrEditLabel"
          data-cy="AgentCreateUpdateHeading"
          v-text="t$('jhipsterApp.agent.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="agent.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="agent.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.agent.lastname')" for="agent-lastname"></label>
            <input
              type="text"
              class="form-control"
              name="lastname"
              id="agent-lastname"
              data-cy="lastname"
              :class="{ valid: !v$.lastname.$invalid, invalid: v$.lastname.$invalid }"
              v-model="v$.lastname.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.agent.firstname')" for="agent-firstname"></label>
            <input
              type="text"
              class="form-control"
              name="firstname"
              id="agent-firstname"
              data-cy="firstname"
              :class="{ valid: !v$.firstname.$invalid, invalid: v$.firstname.$invalid }"
              v-model="v$.firstname.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.agent.age')" for="agent-age"></label>
            <input
              type="number"
              class="form-control"
              name="age"
              id="agent-age"
              data-cy="age"
              :class="{ valid: !v$.age.$invalid, invalid: v$.age.$invalid }"
              v-model.number="v$.age.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.agent.city')" for="agent-city"></label>
            <select class="form-control" id="agent-city" data-cy="city" name="city" v-model="agent.city">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="agent.city && cityOption.id === agent.city.id ? agent.city : cityOption"
                v-for="cityOption in cities"
                :key="cityOption.id"
              >
                {{ cityOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./agent-update.component.ts"></script>
