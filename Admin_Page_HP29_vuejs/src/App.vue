<template>
  <div id="app">
    <div class="app-container">

      <!-- Main Content -->
      <div class="main-content">
        <Header v-if="showHeader"/>
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import Header from "./components/navbar/Header.vue";
import Cookies from "js-cookie";

export default {
  components: {
    Header,
  },
  computed: {
    showHeader() {
      // Only show Header if roles cookie exists and is not null
      const roles = Cookies.get("roles");
      return roles !== undefined && roles !== null;
    }
  }
};
</script>

<style>
.app-container {
  display: flex;
  height: 100vh;
}

.main-content {
  flex-grow: 1;
  overflow-y: auto;
}
</style>
