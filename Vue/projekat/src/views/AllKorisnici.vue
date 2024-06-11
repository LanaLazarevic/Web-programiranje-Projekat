<template>
  <div>
    <table class="table table-striped table-hover">
      <thead>
      <tr>
        <th scope="col">Ime</th>
        <th scope="col">Prezime</th>
        <th scope="col">Email</th>
        <th scope="col">Tip</th>
        <th scope="col">Status</th>
        <th scope="col">Izmena korisnika</th>
        <th scope="col">Izmena statusa</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="korisnik in korisnici" :key="korisnik.korisnik_id">
        <td>{{ korisnik.ime  }}</td>
        <td>{{ korisnik.prezime }}</td>
        <td>{{ korisnik.email }}</td>
        <td>{{ korisnik.tip }}</td>
        <td>{{ korisnik.status }}</td>
        <td><router-link :to="{name: 'IzmenaKorisnika', params: { id: korisnik.korisnik_id, imee:korisnik.ime, prezimee:korisnik.prezime, tipp:korisnik.tip, emaill:korisnik.email }}" class="btn btn-dark">Izmeni</router-link></td>
        <td v-if="korisnik.tip === 'admin'"></td>
        <td v-if="korisnik.tip !== 'admin'"><button @click="changestatus(korisnik.korisnik_id)">Izmena statusa</button></td>
      </tr>
      </tbody>
    </table>
    <div class="pagination">
      <button @click="previousPage" :disabled="currentPage === 1">Nazad</button>
      <span>Stranica {{ currentPage }} od {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Napred</button>
    </div>

    <div class="justify-content-center mt-2">
      <router-link :to="{name: 'NoviKorisnik'}" class="btn btn-primary">Dodaj novog korisnika</router-link>
    </div>

  </div>
</template>

<script>

export default {
  name: "AllKorisnici",
  data() {
    return {
      korisnici: [],
      currentPage:1,
      limit: 5,
      totalPages:0,
    }
  },
  created() {
    this.loadKorisnici(1);
  },
  methods: {
    async loadKorisnici(page) {
      try {
        const response = await this.$axios.get(`/api/users`, {
          params: {
            limit: this.limit,
            page: page
          }
        });
        console.log('API Response:', response);
        console.log('API Response:', response.data);
        this.korisnici = response.data.korisnicii;
        this.totalPages = response.data.stranice;
      } catch (error) {
        console.error('Došlo je do greške pri učitavanju destinacija:', error);
      }
    },
    changestatus(korisnikId) {
      try {
        this.$axios.post(`api/users/status/${korisnikId}`);
        this.loadKorisnici(this.currentPage);
      } catch (error) {
        console.error('Došlo je do greške pri brisanju destinacije:', error);
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.loadKorisnici(this.currentPage);
      }
    },
    previousPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.loadKorisnici(this.currentPage);
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