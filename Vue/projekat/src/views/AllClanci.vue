<template>
  <div>
    <table class="table table-striped table-hover">
      <thead>
      <tr>
        <th scope="col">Clanak</th>
        <th scope="col">Opis</th>
        <th scope="col">Autor</th>
        <th scope="col">Datum</th>
        <th scope="col">Izmena</th>
        <th scope="col">Brisanje</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="clanak in clanci" :key="clanak.clanak_id">
        <td>{{ clanak.naslov  }}</td>
        <td>{{ clanak.tekst | shortText }}</td>
        <td>{{ clanak.autor }}</td>
        <td>{{ clanak.vreme }}</td>
        <td><button @click="updateclanak(clanak.clanak_id)">Izmeni</button></td>
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
  name: "AllClanak",
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
        const response = await this.$axios.get(`/api/clanak/p`, {
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
    deleteclanak(destinacijaId) {
      try {
        this.$axios.delete(`api/clanak/${destinacijaId}`);
        this.loadDestinacije(this.currentPage);
      } catch (error) {
        console.error('Došlo je do greške pri brisanju destinacije:', error);
      }
    },
    updateclanak(destinacijaId) {
      try {
        this.$axios.put(`api/clanak/${destinacijaId}`);
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