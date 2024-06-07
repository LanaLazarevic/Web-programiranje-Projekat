<template>
  <div>
    <table class="table table-striped table-hover">
      <thead>
      <tr>
        <th scope="col">Destinacija</th>
        <th scope="col">Opis</th>
        <th scope="col">Akcija</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="destinacija in destinacije" :key="destinacija.destinacija_id">
        <td>{{ destinacija.ime  }}</td>
        <td>{{ destinacija.opis }}</td>
        <td><button @click="deleteDestinacija(destinacija.destinacija_id)">Obriši</button></td>
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
  name: "AllDestinacije",
  data() {
    return {
      destinacije: [],
      currentPage:1,
      limit: 5,
      totalPages: 0,
    }
  },
  created() {
    this.loadDestinacije(1);
  },
  methods: {
    async loadDestinacije(page) {
      try {
        const response = await this.$axios.get(`/api/dest`, {
          params: {
            limit: this.limit,
            page: page
          }
        });
        console.log('API Response:', response);
        console.log('API Response:', response.data);
        this.destinacije = response.data.destinacijee;
        this.totalPages = response.data.stranice;
      } catch (error) {
        console.error('Došlo je do greške pri učitavanju destinacija:', error);
      }
    },
    deleteDestinacija(destinacijaId) {
      try {
        this.$axios.delete(`api/dest/${destinacijaId}`);
        //ponovno ucitavanje podataka
        this.loadDestinacije(this.currentPage);
      } catch (error) {
        console.error('Došlo je do greške pri brisanju destinacije:', error);
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