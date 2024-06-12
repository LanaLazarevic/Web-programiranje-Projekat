<template>
  <div id="app">
    <NavigationBar :ime="ime" :uloga="uloga" :jwt="jwt" :platforma="platforma" />
    <router-view />
  </div>
</template>

<script>
import NavigationBar from './components/NavigationBar.vue';
import {EventBus} from "@/plugins/event-bus";
export default {
  components: {
    NavigationBar
  },
  data() {
    return {
      ime: '',
      uloga:'',
      jwt:false,
      platforma: true
    };
  },
  created() {
    this.ime = localStorage.getItem('ime') || 'Guest';
    this.uloga = localStorage.getItem('uloga') || '';
    this.jwt = !this.ime.startsWith("Guest");
    this.platforma = this.ime.startsWith("Guest");
    localStorage.setItem("promena", this.platforma);
    EventBus.$on('login-success', (ime) => {
      this.ime = ime;
      this.uloga = localStorage.getItem('uloga') || '';
      this.jwt = !this.ime.startsWith("Guest");
      this.platforma = this.ime.startsWith("Guest");
      localStorage.setItem("promena", this.platforma);
    });
    EventBus.$on('change', () => {
      this.platforma = !this.platforma;
      localStorage.setItem("promena", this.platforma);
    });
  }
};
</script>