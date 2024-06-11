<template>
  <div>
    <ClanciTabela
        :clanci="clanci"
        :destinacije="destinacije"
        :show="false"
    />
    <div class="pagination">
      <button @click="previousPage" :disabled="currentPage === 1">Nazad</button>
      <span>Stranica {{ currentPage }} od {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Napred</button>
    </div>
  </div>
</template>

<script>
import ClanciTabela from "@/components/ClanciTabela.vue";

export default {
  name:'ClanciByAktivnostBezJWT',
  components: {ClanciTabela},
  data() {
    return {
      clanci: [],
      destinacije:[],
      currentPage:1,
      limit: 5,
      totalPages:0,
      id: this.$route.params.id
    };
  },
  methods: {
    async loadClanci(page) {
      try {
        const response = await this.$axios.get(`/api/clanak/aktivnost/` + this.id, {
          params: {
            limit: this.limit,
            page: page
          }
        });
        console.log('API Response:', response);
        console.log('API Response:', response.data);
        this.clanci = response.data.clancii;
        this.totalPages = response.data.stranice;
        const listaid = [...new Set(this.clanci.map(clanak => clanak.destinacija))];
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
        this.loadClanci(this.currentPage);
      }
    },
    previousPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.loadClanci(this.currentPage);
      }
    }
  },
  created() {
    this.loadClanci(1);
    console.log("usao");
  },

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