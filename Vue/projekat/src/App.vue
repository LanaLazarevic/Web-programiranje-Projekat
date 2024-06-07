<template>
  <div id="app">
    <NavigationBar :ime="ime" :uloga="uloga" /> <!-- Prosleđujemo ime kao prop -->
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
      uloga:''
    };
  },
  created() {
    this.ime = localStorage.getItem('ime') || 'Guest';
    this.uloga = localStorage.getItem('uloga') || '';
    EventBus.$on('login-success', (ime) => {
      console.log('Login successful, ime:', ime);
      this.ime = ime;
      this.uloga = localStorage.getItem('uloga') || '';
    });
  }
};
</script>