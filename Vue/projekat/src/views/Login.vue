<template>
  <div class="pt-5">
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="email">Email</label>
        <input v-model="email" type="email" class="form-control" id="email" placeholder="Unesite Vas email">
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input v-model="password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Sifra">
      </div>
      <button type="submit" class="btn btn-primary mt-2">Submit</button>
    </form>
  </div>
</template>

<script>
import {EventBus} from "@/plugins/event-bus";

export default {
  name: "LoginPage",
  data() {
    return {
      email: '',
      password: '',
    }
  },
  methods: {
    login() {
      this.$axios.post('/api/users/login', {
        email: this.email,
        password: this.password,
      }).then(response => {
        localStorage.setItem('jwt', response.data.jwt)
        if (this.$route.name !== 'PocetnaStrana') {
          this.$router.push({name: 'PocetnaStrana'});
        }

        EventBus.$emit('login-success', localStorage.getItem("ime"));
      })
    }
  },
}
</script>

<style scoped>

</style>