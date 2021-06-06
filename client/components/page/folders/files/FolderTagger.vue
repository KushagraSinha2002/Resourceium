<template>
  <div
    class="flex justify-between px-2 pt-2 pb-1 border border-gray-400 rounded-tl-15px rounded-br-15px"
  >
    <div>
      {{ tags }}
      <div v-for="tag in tags" :key="tag.id">{{ tag }}</div>
    </div>
    <div class="flex flex-col items-center">
      <v-swatches v-model="colour" show-fallback></v-swatches>
      <div
        class="flex items-center justify-center w-6 h-6 rounded-full cursor-pointer hover:bg-black hover:bg-opacity-20"
        title="Add Tag"
        @click.stop.prevent="addTag"
      >
        <ig-icon
          name="plus"
          variant="success"
          size="sm"
          class="!block mx-auto"
        ></ig-icon>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    folderID: { type: Number, required: true },
  },
  data() {
    return {
      colour: '#A463BF',
      name: 'Hello',
      tags: null,
    }
  },
  async fetch() {
    this.tags = await this.$axios.$get(`/tags/get/${this.folderID}`)
  },
  methods: {
    async addTag() {
      // await console.log(`/tags/create/${this.folder.id}`)
      const resp = await this.$axios.$post(`/tags/create/${this.folderID}`, {
        name: this.name,
        colour: this.colour,
      })
      console.log(resp)
    },
  },
}
</script>
