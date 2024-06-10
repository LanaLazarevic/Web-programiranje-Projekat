<template>
  <div>
    <DestinacijeTabela
        :destinacije="destinacije"
        :show="false"
        :putanja="false"/>
    <div class="pagination">
      <button @click="previousPage" :disabled="currentPage === 1">Nazad</button>
      <span>Stranica {{ currentPage }} od {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Napred</button>
    </div>
  </div>
</template>

<script>
import DestinacijeTabela from "@/components/DestinacijeTabela.vue";
export default {
  name: "AllDestinacijeBez",
  components: {DestinacijeTabela},
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
        const response = await this.$axios.get(`/api/dest/sve`, {
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