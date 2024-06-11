<template>
  <form  @submit.prevent="submitForm">
    <div class="mb-3">
      <label for="imedest" class="form-label">Ime destinacije</label>
      <input   v-model="ime" class="form-control" id="imedest">
    </div>
    <div class="mb-3">
      <label for="opisdest" class="form-label">Opis destinacije</label>
      <textarea v-model="opis" class="form-control" id="opisdest"></textarea>
    </div>
    <button type="submit" class="btn btn-primary">Dodaj</button>
  </form>
</template>

<script>
export default {
  name:'NovaDestinacija',
  data() {
    return {
        ime: '',
        opis: '',
        message:'',
    };
  },
  methods: {
    async submitForm() {
      try {
        if(this.provera(this.ime) || this.provera(this.opis)){
          alert("Sva polja moraju biti popunjena.");
          return;
        }
        const response = await this.$axios.post('/api/dest', {
          ime: this.ime,
          opis: this.opis,

        });
        alert(response.data.poruka);

      } catch (error) {
        console.error('Error adding destination:', error);
        alert(error);
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