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
            <li class="nav-item">
              <router-link :to="{name: 'AllDestinacije'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AllDestinacije'}">Destinacije</router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{name: 'AllClanak'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AllClanak'}">Clanci</router-link>
            </li>
          </ul>
          <h2>{{ ime }}</h2>
          <form v-if="canLogout" class="d-flex" @submit.prevent="logout">
            <button class="btn btn-outline-secondary" type="submit">Logout</button>
          </form>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import HomeView from "@/views/HomeView.vue";

export default {
  name: "NavigationBar",
  // eslint-disable-next-line vue/no-unused-components
  components: {HomeView},
  props: ['ime'], // Dodajemo ime kao prop

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
      this.$router.push({name: 'Login'});
    }
  }
}
</script>

<style scoped>

</style>
