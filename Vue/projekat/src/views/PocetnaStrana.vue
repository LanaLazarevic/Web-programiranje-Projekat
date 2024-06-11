<template>
  <div>
    <ClanciTabela
        :clanci="clanci"
        :destinacije="destinacije"
        :show="false"
    />
  </div>
</template>

<script>

import ClanciTabela from "@/components/ClanciTabela.vue";

export default {
  name: "PocetnaStrana",
  components: {ClanciTabela},
  data() {
    return {
      clanci: [],
      destinacije: [],
      listaid:[],
    }
  },
  created() {
    this.loadClanci();
  },
  methods: {
    async loadClanci() {
      try {
        const response = await this.$axios.get(`/api/clanak/sve/naj`, {
          params: {
            limit: 10,
            page: 1
          }
        });
        console.log('API Response:', response.data);
        this.clanci = response.data.clancii;

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
    }
  }
}
</script>

<style>

</style>