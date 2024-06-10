
<template>
  <table class="table table-striped table-hover">
    <thead>
    <tr>
      <th scope="col">Destinacija</th>
      <th scope="col">Opis</th>
      <th v-if="show" scope="col">Obrisi</th>
      <th v-if="show" scope="col">Izmeni</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="destinacija in destinacije" :key="destinacija.destinacija_id">
      <td>
        <router-link :to="putanja ? { name: 'ClanciByDestinacija', params: { id: destinacija.destinacija_id, ime: destinacija.ime } } : { name: 'ClanciByDestinacijaBezJWT', params: { id: destinacija.destinacija_id, ime: destinacija.ime } }">          {{ destinacija.ime }}
        </router-link>
      </td>
      <td>{{ destinacija.opis }}</td>
      <td v-if="show"><button @click="$emit('delete', destinacija.destinacija_id)">Obriši</button></td>
      <td v-if="show">
        <router-link :to="{ name: 'IzmenaDestinacije', params: { id: destinacija.destinacija_id, imee: destinacija.ime, opiss: destinacija.opis }}" class="btn btn-dark">Izmeni</router-link>
      </td>
    </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  props: {
    destinacije: {
      type: Array,
      required: true
    },
    show: {
      type: Boolean,
      default: false
    },
    putanja: {
      type:Boolean,
      default: false
    }
  }
}
</script>
