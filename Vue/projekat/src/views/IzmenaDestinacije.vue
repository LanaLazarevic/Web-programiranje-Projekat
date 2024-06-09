<template>
  <form  @submit.prevent="submitForm">
    <div class="mb-3">
      <label for="imedest" class="form-label">Ime destinacije</label>
      <input   v-model="ime" class="form-control" id="imedest" >
    </div>
    <div class="mb-3">
      <label for="opisdest" class="form-label">Opis destinacije</label>
      <textarea v-model="opis" class="form-control" id="opisdest"></textarea>
    </div>
    <button type="submit" class="btn btn-primary">Izmeni</button>
  </form>
</template>

<script>
export default {
  name:'IzmenaDestinacije',
  props:{
    imee:String,
    opiss:String,
    id:Number,
  },
  data() {
    return {
      ime: this.imee,
      opis: this.opiss,
      message:'',
    };
  },
  methods: {
    async submitForm() {
      try {
        const response = await this.$axios.put('/api/dest', {
          ime: this.ime,
          opis: this.opis,
          destinacija_id: this.id
        });
        if(response.data)
          this.message = 'Destinacija uspešno izmenjena!';
        else
          this.message = 'Destinacija nije izmenjena.'

        alert(this.message);

      } catch (error) {
        console.error('Error adding destination:', error);
        alert(error);
      }
    }
  }
};
</script>

<style scoped>

</style>