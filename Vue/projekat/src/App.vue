<template>
  <div id="app">
    <NavigationBar :ime="ime" /> <!-- Prosleđujemo ime kao prop -->
    <router-view/>
  </div>
</template>

<script>
import NavigationBar from './components/NavigationBar.vue';

export default {
  components: {
    NavigationBar
  },
  data() {
    return {
      ime: '' // Definišemo ime u data objektu
    };
  },
  created() {
    // Postavljamo ime iz localStorage ili drugog izvora
    this.ime = localStorage.getItem('ime') || 'Guest';
  }
};
</script>
