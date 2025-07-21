<template>
  <div id="app">
    <div class="app-container">

      <!-- Main Content -->
      <div class="main-content">
        <Header/>
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import Header from "./components/navbar/Header.vue";

export default {
  components: {
    Header,
  },
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
