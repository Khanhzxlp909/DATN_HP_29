<!-- src/App.vue -->
<template>
  <div id="app">
    <Navbar />
    <router-view />
    <MainFooter/>
  </div>
</template>

<script>
import Navbar from './components/MainNavbar.vue';
import MainFooter from './components/MainFooter.vue';
export default {
  components: {
    Navbar,
    MainFooter,
  },
};
</script>

<style>
#app {
  min-height: 100vh;         /* chiều cao full màn hình */
  display: flex;
  flex-direction: column;
}

router-view {
  flex: 1;                   /* phần nội dung sẽ giãn ra chiếm hết */
}

footer {
  background: #f5f5f5;
  padding: 1rem;
  text-align: center;
}

</style>