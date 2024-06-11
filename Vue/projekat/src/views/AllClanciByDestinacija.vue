<template>
  <div>
    <ClanciTabela
        :clanci="clanci"
        :destinacije="destinacije"
        @delete="deleteclanak"
        :show="true"
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
  name:'ClanciByDestinacija',
  components: {ClanciTabela},
  data() {
    return {
      clanci: [],
      destinacije: [],
      currentPage:1,
      limit: 5,
      totalPages:0,
      id:this.$route.params.id
    };
  },
  methods: {
    async loadClanci(page) {
      try {
        const response = await this.$axios.get(`/api/clanak/sve/` + this.id, {
          params: {
            limit: this.limit,
            page: page
          }
        });
        console.log('API Response:', response);
        console.log('API Response:', response.data);
        this.clanci = response.data.clancii;
        this.totalPages = response.data.stranice;
        this.destinacije.push(new Object(this.id, this.ime));
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
    },async deleteclanak(destinacijaId) {
      try {
        // eslint-disable-next-line no-unused-vars
        const response = await this.$axios.delete(`api/clanak/${destinacijaId}`);
        alert(response.data.poruka);
        this.loadClanci(this.currentPage);
      } catch (error) {
        console.error('Došlo je do greške pri brisanju destinacije:', error);
      }
    }
  },
  created() {
    this.loadClanci(1);
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