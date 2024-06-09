<template>
  <div>
    <table class="table table-striped table-hover">
      <thead>
      <tr>
        <th scope="col">Clanak</th>
        <th scope="col">Opis</th>
        <th scope="col">Destinacija</th>
        <th scope="col">Datum</th>
        <th scope="col">Izmena</th>
        <th scope="col">Brisanje</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="clanak in clanci" :key="clanak.clanak_id">
        <td>{{ clanak.naslov  }}</td>
        <td>{{ clanak.tekst | shortText }}</td>
        <td>{{ ime }}</td>
        <td>{{ clanak.vreme }}</td>
        <td><button @click="updateclanak()">Izmeni</button></td>
        <td><button @click="deleteclanak(clanak.clanak_id)">Obriši</button></td>
      </tr>
      </tbody>
    </table>
    <div class="pagination">
      <button @click="previousPage" :disabled="currentPage === 1">Nazad</button>
      <span>Stranica {{ currentPage }} od {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Napred</button>
    </div>
  </div>
</template>

<script>
export default {
  name:'ClanciByDestinacija',
  filters: {
    shortText(value) {
      if (value.length < 30) {
        return value;
      }
      return value.slice(0, 30) + '...'
    }
  },
  props: {
    id: {
      type: Number,
      required: true
    },
    ime:{
      type:String,
      required: true
    }
  },
  data() {
    return {
      clanci: [],
      currentPage:1,
      limit: 5,
      totalPages:0,
    };
  },
  methods: {
    async loadClanci(page) {
      try {
        const response = await this.$axios.get(`/api/clanak/` + this.id, {
          params: {
            limit: this.limit,
            page: page
          }
        });
        console.log('API Response:', response);
        console.log('API Response:', response.data);
        this.clanci = response.data.clancii;
        this.totalPages = response.data.stranice;
      } catch (error) {
        console.error('Došlo je do greške pri učitavanju destinacija:', error);
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.loadDestinacije(this.currentPage);
      }
    },
    previousPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.loadDestinacije(this.currentPage);
      }
    }
  },
  created() {
    this.loadClanci(1);
  },
  async deleteclanak(destinacijaId) {
    try {
      // eslint-disable-next-line no-unused-vars
      const response = await this.$axios.delete(`api/clanak/${destinacijaId}`);
      this.loadDestinacije(this.currentPage);
    } catch (error) {
      console.error('Došlo je do greške pri brisanju destinacije:', error);
    }
  },
  updateclanak() {

  }
};
</script>
<style>
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
}

.pagination button {
  margin: 0 5px;
}
</style>