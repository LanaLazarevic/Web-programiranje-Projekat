<template>
  <div>
    <table class="table table-striped table-hover">
      <thead>
      <tr>
        <th scope="col">Clanak</th>
        <th scope="col">Opis</th>
        <th scope="col">Destinacija</th>
        <th scope="col">Datum</th>
        <th scope="col">Saznaj više</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="clanak in clanci" :key="clanak.clanak_id">
        <td>{{ clanak.naslov  }}</td>
        <td>{{ clanak.tekst | shortText }}</td>
        <td>{{ getDestinacijaName(clanak.destinacija) }}</td>
        <td>{{ clanak.vreme }}</td>
        <td> <router-link :to="{ name: 'AClanak', params: { id: clanak.clanak_id } }" class="btn btn-dark">...</router-link></td>
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
  name: "PocetnaStrana",
  filters: {
    shortText(value) {
      if (value.length < 30) {
        return value;
      }
      return value.slice(0, 30) + '...'
    }
  },
  data() {
    return {
      clanci: [],
      destinacije: [],
      listaid:[],
      currentPage:1,
      limit: 5,
      totalPages:0,
    }
  },
  created() {
    this.loadClanci(1);
  },
  methods: {
    async loadClanci(page) {
      try {
        const response = await this.$axios.get(`/api/clanak/naj`, {
          params: {
            limit: this.limit,
            page: page
          }
        });
        console.log('API Response:', response.data);
        this.clanci = response.data.clancii;
        this.totalPages = response.data.stranice;

        const listaid = this.clanci.map(clanak => clanak.destinacija);

        const idsString = listaid.join(',');

        const response2 = await this.$axios.get(`/api/dest/ids`, {
          params: {
            limit: 501,
            page: 1,
            ids: idsString,
          }
        });
        console.log('API Response:', response2.data);
        this.destinacije = response2.data.destinacije;
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
    },
    findDestinaciju(destinacijaId) {
      return this.destinacije.find(dest => dest.destinacija_id === destinacijaId) || null;
    },
    getDestinacijaName(destinacijaId) {
      const destinacija = this.findDestinaciju(destinacijaId);
      return destinacija ? destinacija.ime : '';
    }
  }
}
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