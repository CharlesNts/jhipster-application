<template>
  <div>
    <b-dropdown-item to="/city">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.city')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/adress">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.adress')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/agent">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.agent')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
