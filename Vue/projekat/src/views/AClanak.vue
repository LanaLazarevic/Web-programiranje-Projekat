<template>
  <div class="container mt-5 text-center">
    <div>
      <h1>{{ clanak.naslov }}</h1>
      <p class="text-muted">Autor: {{ clanak.autor }}</p>
      <p class="text-muted">Datum: {{ clanak.vreme }}</p>
      <p class="text-muted">Destinacija: {{ destinacijaa }}</p>
      <p><strong>Aktivnosti:</strong></p>
      <ul>
        <li v-for="aktivnost in aktivnostii" :key="aktivnost.aktivnost_id">
          <router-link  v-if="aktivnost" :to=" jwt ? `/sviclancipoaktivnosti/${aktivnost.aktivnost_id}` : `/clancipoaktivnosti/${aktivnost.aktivnost_id}`">
            {{ aktivnost.naziv }}
          </router-link>
        </li>      </ul>

      <p>{{ clanak.tekst }}</p>

      <h4>Dodaj komentar</h4>
      <form @submit.prevent="submit">
        <div class="mb-3">
          <label for="autor" class="form-label">Ime</label>
          <input type="text" class="form-control" id="autor" v-model="autorr" required>
        </div>
        <div class="mb-3">
          <label for="teks" class="form-label">Komentar</label>
          <textarea class="form-control" id="tekst" rows="3" v-model="komm" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Pošalji</button>
      </form>

      <h3 v-if="kom.length>0">Komentari</h3>
      <ul class="list-group mb-3">
        <li v-for="komentar in kom" :key="komentar.komentar_id" class="list-group-item">
          <p><strong>{{ komentar.autor }}</strong></p>
          <p>{{ komentar.tekst }}</p>
          <small class="text-muted">{{ komentar.datum }}</small>
        </li>
      </ul>

    </div>
  </div>
</template>

<script>
  export default {
    name:'AClanak',
    data() {
      return {
        clanak: {},
        kom: [],
        aktivnostii:[],
        destinacijaa:'',
        autorr: '',
        komm: '',
        jwt : false,
        id:this.$route.params.id
      };
    },
    created() {
      this.loadclanak();
      if(localStorage.getItem('jwt'))
        this.jwt=true;
      this.$axios.post(`/api/clanak/brposeta/`+this.id)
    },
    methods: {
      async loadclanak() {
        try {
          const response = await this.$axios.get(`/api/clanak/byid/`+this.id);
          this.clanak = response.data;

          const response2 = await this.$axios.get(`/api/kom/` + this.id);
          this.kom = response2.data;

          const listaid = this.clanak.aktivnosti;
          const idsString = listaid.join(',');
          const response3 = await this.$axios.get(`/api/akt/sve`, {
            params: {
              ids: idsString,
            }
          });
          this.aktivnostii = response3.data;

          const response4 = await this.$axios.get(`/api/dest/ime/`+this.clanak.destinacija);
          this.destinacijaa = response4.data.ime;



        } catch (error) {
          console.error('Error fetching article:', error);
        }
      },
      async submit() {
        try {
          const response = await this.$axios.post('/api/kom/' + this.id, {
            autor: this.autorr,
            tekst: this.komm,
            datum : new Date().toISOString().slice(0, 10),
          });

          this.kom.push(response.data);
          this.autorr = '';
          this.komm = '';

        } catch (error) {
          console.error('Error adding kom:', error);
        }
      }
    }
  };
</script>

<style scoped>
.container {
  max-width: 800px;
}
</style>
