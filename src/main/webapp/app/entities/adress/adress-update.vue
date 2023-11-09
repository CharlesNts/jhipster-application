<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.adress.home.createOrEditLabel"
          data-cy="AdressCreateUpdateHeading"
          v-text="t$('jhipsterApp.adress.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="adress.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="adress.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.adress.streetNumb')" for="adress-streetNumb"></label>
            <input
              type="text"
              class="form-control"
              name="streetNumb"
              id="adress-streetNumb"
              data-cy="streetNumb"
              :class="{ valid: !v$.streetNumb.$invalid, invalid: v$.streetNumb.$invalid }"
              v-model="v$.streetNumb.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.adress.streetName')" for="adress-streetName"></label>
            <input
              type="text"
              class="form-control"
              name="streetName"
              id="adress-streetName"
              data-cy="streetName"
              :class="{ valid: !v$.streetName.$invalid, invalid: v$.streetName.$invalid }"
              v-model="v$.streetName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.adress.agent')" for="adress-agent"></label>
            <select class="form-control" id="adress-agent" data-cy="agent" name="agent" v-model="adress.agent">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="adress.agent && agentOption.id === adress.agent.id ? adress.agent : agentOption"
                v-for="agentOption in agents"
                :key="agentOption.id"
              >
                {{ agentOption.id }}
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
<script lang="ts" src="./adress-update.component.ts"></script>
