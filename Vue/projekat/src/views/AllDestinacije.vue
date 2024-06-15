<template>
  <div>
    <DestinacijeTabela
        :destinacije="destinacije"
        @delete="deleteDestinacija"
        :show="true"
        :putanja="true"/>
    <div class="pagination">
      <button @click="previousPage" :disabled="currentPage === 1">Nazad</button>
      <span>Stranica {{ currentPage }} od {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Napred</button>
    </div>
    <div class="justify-content-center mt-2">
      <router-link :to="{name: 'NovaDestinacija'}" class="btn btn-primary">Dodaj novu destinaciju</router-link>
    </div>
  </div>
</template>

<script>
import DestinacijeTabela from "@/components/DestinacijeTabela.vue";
import {EventBus} from "@/plugins/event-bus";
export default {
  name: "AllDestinacije",
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
    if(localStorage.getItem('jwt')!==null && localStorage.getItem('promena')==='true'){
      EventBus.$emit('change');
      console.log("promenio");
    }
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
    async deleteDestinacija(destinacijaId) {
      try {
        const response = await this.$axios.delete(`api/dest/${destinacijaId}`);
        alert(response.data.poruka);
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