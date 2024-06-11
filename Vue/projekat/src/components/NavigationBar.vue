<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">Web programiranje</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item" v-if="platforma === true">
              <router-link :to="{name: 'PocetnaStrana'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'PocetnaStrana'}">Pocetna</router-link>
            </li>
            <li class="nav-item" v-if="platforma === true">
              <router-link :to="{name: 'NajCitanije'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'NajCitanije'}">Najcitaniji clanci</router-link>
            </li>
            <li class="nav-item" v-if="platforma === true">
              <router-link :to="{name: 'AllDestinacijeBezJWT'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AllDestinacijeBezJWT'}">Destinacije</router-link>
            </li>
            <li class="nav-item" v-if="jwt === true && platforma === false">
              <router-link :to="{name: 'AllDestinacije'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AllDestinacije'}">Sve Destinacije</router-link>
            </li>
            <li class="nav-item" v-if="jwt === true && platforma === false">
              <router-link :to="{name: 'AllClanak'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AllClanak'}">Svi Clanci</router-link>
            </li>
            <li class="nav-item" v-if="jwt === true && uloga === 'admin' && platforma === false">
              <router-link :to="{name: 'AllKorisnici'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AllKorisnici'}">Korisnici</router-link>
            </li>
            <li class="nav-item" v-if="jwt === false && platforma === true">
              <router-link :to="{name: 'Login'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Login'}">Login</router-link>
            </li>
          </ul>
          <h2 class="m-2" v-if="platforma === false">{{ ime }}</h2>
          <form v-if="jwt === true && platforma === false" class="d-flex" @submit.prevent="logout">
            <button class="btn btn-outline-secondary" type="submit">Logout</button>
          </form>
          <form v-if="jwt === true && platforma === true" class="d-flex" @submit.prevent="backk">
            <button class="btn btn-outline-secondary" type="submit">Nazad na CMS</button>
          </form>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import {EventBus} from "@/plugins/event-bus";

export default {
  name: "NavigationBar",

  props: ['ime', 'uloga', 'jwt', 'platforma'],

  computed: {
    canLogout() {
      return this.$route.name !== 'Login';
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('jwt');
      localStorage.removeItem('ime');
      localStorage.removeItem('uloga');
      localStorage.removeItem('prezime');
      if(this.$route.path !== '/')
        this.$router.push({name: 'PocetnaStrana'});
      EventBus.$emit('login-success', 'Guest');
    },
    backk(){
      EventBus.$emit('change');
      this.$router.push(`/svedestinacije`);
    }
  }
}
</script>

<style scoped>

</style>
