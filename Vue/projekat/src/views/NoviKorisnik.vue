<template>
  <form  @submit.prevent="submitForm">
    <div class="mb-3">
      <label for="imedest" class="form-label">Ime korisnika</label>
      <input   v-model="ime" class="form-control" id="imedest">
    </div>
    <div class="mb-3">
      <label for="prezime" class="form-label">Prezime korisnika</label>
      <input   v-model="prezime" class="form-control" id="prezime">
    </div>
    <div class="mb-3">
      <label for="email" class="form-label">Email korisnika</label>
      <input   v-model="email" class="form-control" id="email" type="email">
    </div>
    <div class="mb-3">
      <label for="tip" class="form-label">Tip korisnika</label>
      <input   v-model="tip" class="form-control" id="tip">
    </div>
    <div class="mb-3">
      <label for="lozinka" class="form-label">Lozinka korisnika</label>
      <input   v-model="lozinka" class="form-control" id="lozinka" type="password">
    </div>
    <div class="mb-3">
      <label for="lozinka2" class="form-label">Ponovo unesite lozinku korisnika</label>
      <input   v-model="lozinka2" class="form-control" id="lozinka2" type="password">
    </div>
    <button type="submit" class="btn btn-primary">Dodaj</button>
  </form>
</template>

<script>
export default {
  name:'NoviKorisnik',
  data() {
    return {
        ime: '',
        prezime: '',
        tip:'',
        lozinka:'',
        email:'',
        lozinka2:'',
    };
  },
  methods: {
    async submitForm() {
      try {
        if(this.lozinka===this.lozinka2) {
          if(this.provera(this.ime) ||  this.provera(this.prezime) ||  this.provera(this.tip) ||  this.provera(this.email) ||  this.provera(this.lozinka)){
            alert("Sva polja moraju biti popunjena.");
            return;
          }

          const response = await this.$axios.post('/api/users', {
            ime: this.ime,
            prezime: this.prezime,
            tip: this.tip,
            email: this.email,
            lozinka: this.lozinka,
            status: 'aktivan',
          });

          this.ime = '';
          this.prezime = '';
          this.lozinka2 = '';
          this.tip = '';
          this.email = '';
          this.lozinka = '';
          alert(response.data.poruka);

        }
        else {
          alert("Lozinke moraju da se poklapaju.")
        }


      } catch (error) {
        console.error('Error adding destination:', error);
        alert("Greska prilikom izmene");
      }
    },
    provera(string){
      return string === ' ' || string ==='';
    }
  }
};
</script>

<style scoped>

</style>