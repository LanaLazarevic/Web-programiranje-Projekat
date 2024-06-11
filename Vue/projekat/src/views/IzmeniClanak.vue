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

    <button type="submit" class="btn btn-primary">Izmeni</button>
  </form>
</template>

<script>
export default {
  name:'IzmeniClanak',
  props:{
    id:Number,
    naslovv: String,
    destinacijaa: Object,
    tekstt:String,
    aktivnostii:[],
  },
  data() {
    return {
      naslov: this.naslovv,
      destinacija: '',
      destinacije:[],
      tekst:this.tekstt,
      aktivnosti: [],
      novaaktinvost:'',
    };
  },
  created() {
    this.getaktivnosti(this.aktivnostii)
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
        this.destinacija = this.destinacijaa.destinacija_id
      } catch (error) {
        console.error('Došlo je do greške pri učitavanju destinacija:', error);
      }
    },
    async getaktivnosti(aktivnsotiid) {
      try {
        const response = await this.$axios.get(`/api/akt/sve`, {
          params: {
            ids: aktivnsotiid.join(',')
          }
        });
        console.log('API Response:', response);
        console.log('API Response:', response.data);
        this.aktivnosti = response.data;
      } catch (error) {
        console.error('Došlo je do greške pri učitavanju destinacija:', error);
      }
    },
    async dodajaktivnost() {
      if (!this.novaaktinvost){
        return;
      } else if (this.novaaktinvost === ' ' || this.novaaktinvost === '') {
        alert("Polje za aktivnost ne sme da bude prazno");
      }
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
        if(this.provera(this.naslov) ||  this.provera(this.tekst) ||  this.provera(this.destinacija)){
            alert("Sva polja moraju biti popunjena.");
            return;
        }

        const response = await this.$axios.put('/api/clanak/', {
          clanak_id:this.id,
          naslov: this.naslov,
          tekst: this.tekst,
          destinacija: this.destinacija,
          aktivnosti: aktivnostiIds,
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