<template>
  <form  @submit.prevent="submitForm">
    <div class="mb-3">
      <label for="naslov" class="form-label">Naslov clanka</label>
      <input   v-model="naslov" class="form-control" id="naslov">
    </div>
    <div class="mb-3">
      <label for="dest" class="form-label">Destinacija clanka</label>
      <select v-model="destinacija" class="form-control" id="dest">
        <option v-for="destinacija in destinacije" :key="destinacija.destinacija_id" :value="destinacija.destinacija_id">
          {{ destinacija.ime }}
        </option>
      </select>
    </div>
    <div class="mb-3">
      <label for="tekst" class="form-label">Tekst clanka</label>
      <input   v-model="tekst" class="form-control" id="email" type="tekst">
    </div>
    <div class="mb-3">
      <label for="aktivnosti" class="form-label">Aktivnosti</label>
      <div class="input-group">
        <input v-model="novaaktinvost" class="form-control" id="aktivnosti">
        <button @click="dodajaktivnost" class="btn btn-secondary" type="button">Dodaj</button>
      </div>
      <div class="mt-2">
        <span v-for="(aktivnost, index) in aktivnosti" :key="aktivnost.aktivnost_id" class="badge bg-primary me-2">
          {{ aktivnost.naziv }}
          <button @click="ukloniaktivnost(index)" type="button" class="btn btn-danger btn-sm ms-2">X</button>
        </span>
      </div>
    </div>

    <button type="submit" class="btn btn-primary">Dodaj</button>
  </form>
</template>

<script>
export default {
  name:'NoviClanak',
  data() {
    return {
      naslov: '',
      destinacija: '',
      destinacije:[],
      tekst:'',
      aktivnosti:[],
      novaaktinvost:'',
    };
  },
  created() {
    this.load();
  },
  methods: {
    async load() {
      try {
        const response = await this.$axios.get(`/api/dest/sve`, {
          params: {
            limit: 501,
            page: 1
          }
        });
        console.log('API Response:', response);
        console.log('API Response:', response.data);
        this.destinacije = response.data.destinacijee;
      } catch (error) {
        console.error('Došlo je do greške pri učitavanju destinacija:', error);
      }
    },
    async dodajaktivnost() {
      if (!this.novaaktinvost) return;
      try {
        const response = await this.$axios.post('/api/akt', {
          naziv: this.novaaktinvost
        });
        console.log(response.data);
        const nova = response.data
        this.aktivnosti.push(nova);
        console.log(this.aktivnosti);
        this.novaaktinvost = '';
      } catch (error) {
        console.error('Error:', error);
      }
    },
    ukloniaktivnost(index) {
      this.aktivnosti.splice(index, 1);
      console.log(this.aktivnosti);
    },
    async submitForm() {
      try {
        const aktivnostiIds = this.aktivnosti.map(aktivnost => aktivnost.aktivnost_id);
        console.log(this.aktivnosti);
        console.log(aktivnostiIds);

        // eslint-disable-next-line no-unused-vars
          const response = await this.$axios.post('/api/clanak', {
            naslov: this.naslov,
            tekst: this.tekst,
            destinacija: this.destinacija,
            aktivnosti: aktivnostiIds,
            autor: localStorage.getItem("ime") + ' ' + localStorage.getItem("prezime"),
            vreme: new Date().toISOString().slice(0, 10)
          });

          this.naslov = '';
          this.tekst = '';
          this.destinacija = '';
          this.tip = '';

      } catch (error) {
        console.error('Error adding destination:', error);
      }
    }
  }
};
</script>

<style scoped>
.input-group {
  display: flex;
  align-items: center;
}

.input-group button {
  margin-left: 5px;
}

.badge {
  display: inline-block;
  padding: 0.5em 1em;
  margin-bottom: 5px;
}

</style>