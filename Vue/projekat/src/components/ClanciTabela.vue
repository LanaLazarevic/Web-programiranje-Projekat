
<template>
  <table class="table table-striped table-hover">
    <thead>
    <tr>
      <th scope="col">Clanak</th>
      <th scope="col">Opis</th>
      <th  v-if="show" scope="col">Autor</th>
      <th scope="col">Destinacija</th>
      <th scope="col">Datum</th>
      <th v-if="show" scope="col">Izmena</th>
      <th v-if="show" scope="col">Brisanje</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="clanak in clanci" :key="clanak.clanak_id">
      <td>
        <router-link :to="`/clanak/${clanak.clanak_id}`">
          {{ clanak.naslov }}
        </router-link>
      </td>
      <td>{{ clanak.tekst | shortText }}</td>
      <td  v-if="show" >{{ clanak.autor }}</td>
      <td>{{ getDestinacijaName(clanak.destinacija) }}</td>
      <td>{{ clanak.vreme }}</td>
      <td v-if="show">
        <router-link :to="{ name: 'IzmeniClanak', params: { id: clanak.clanak_id, naslovv: clanak.naslov, tekstt: clanak.tekst, destinacijaa: findDestinaciju(clanak.destinacija), aktivnostii: clanak.aktivnosti}}" class="btn btn-dark">Izmeni</router-link>
      </td>
      <td><button v-if="show" @click="$emit('delete', clanak.clanak_id)">Obriši</button></td>
    </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  props: {
    clanci: {
      type: Array,
      required: true
    },
    destinacije: {
      type: Array,
      required: true
    },
    show: {
      type: Boolean,
      default: false
    }
  },
  filters: {
    shortText(value) {
      if (value.length < 30) {
        return value;
      }
      return value.slice(0, 30) + '...';
    }
  },
  methods: {
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
