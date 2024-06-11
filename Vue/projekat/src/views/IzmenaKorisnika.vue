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
    <button type="submit" class="btn btn-primary">Izmeni</button>
  </form>
</template>

<script>
export default {
  name:'IzmenaKorisnika',
  props:{
    imee: String,
    prezimee: String,
    tipp:String,
    emaill:String,
    id:Number,
  },
  data() {
    return {
      ime: this.imee,
      prezime: this.prezimee,
      tip:this.tipp,
      email:this.emaill,
    };
  },
  methods: {
    async submitForm() {
      try {
        if(this.provera(this.ime) ||  this.provera(this.prezime) ||  this.provera(this.tip) ||  this.provera(this.email) ){
          alert("Sva polja moraju biti popunjena.");
          return;
        }
        const response = await this.$axios.put('/api/users', {
          ime: this.ime,
          prezime: this.prezime,
          tip:this.tip,
          email: this.email,
          korisnik_id: this.id
        });
        alert(response.data.poruka);

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