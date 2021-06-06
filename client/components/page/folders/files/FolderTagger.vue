<template>
  <div
    class="px-2 pt-2 pb-2 border border-gray-400 rounded-tl-15px rounded-br-15px"
  >
    <div class="grid grid-cols-2 gap-3 sm:grid-cols-3">
      <div
        v-for="tag in tags"
        :key="tag.id"
        class="flex items-center space-x-2"
      >
        <div
          class="flex items-end justify-end w-10 h-10 p-0.5 rounded-lg"
          :style="{ backgroundColor: tag.colour }"
        >
          <ig-icon
            name="trash"
            no-color
            class="text-black transition-transform origin-bottom-right transform cursor-pointer hover:scale-110"
            size="sm"
            :title="`Delete tag ${tag.name}`"
            @click.native="removeTag(tag.id)"
          ></ig-icon>
        </div>
        <div>{{ tag.name }}</div>
      </div>
    </div>
    <form
      v-if="tags.length < 6"
      class="flex items-center space-x-3"
      @submit.prevent="addTag"
    >
      <v-swatches v-model="colour" show-fallback class="mt-1"></v-swatches>
      <input
        v-model="name"
        type="text"
        class="block rounded-md appearance-none ring focus:outline-none"
        required
      />
      <button
        type="submit"
        class="font-semibold text-purple-800 underline font-poppins"
      >
        Add Tag
      </button>
    </form>
  </div>
</template>

<script>
export default {
  props: {
    folderId: { type: Number, required: true },
  },
  data() {
    return {
      colour: '#A463BF',
      name: '',
      tags: [],
      newTag: null,
    }
  },
  async fetch() {
    this.tags = await this.$axios.$get(`/tags/folder/${this.folderId}`)
  },
  methods: {
    getTagDetails(index) {
      const tag = this.tags.find((tag) => tag.id === index)
      let template = '<ol style="text-align: left">'
      template += `
        <li>Name: ${tag.name}</li>
      `
      template += '</ol>'
      return template
    },
    async removeTag(tagID) {
      await this.$axios.$post(`/tags/remove/${tagID}/${this.folderId}`)
      this.$fetch()
    },
    async addTag() {
      await this.$axios.$post(`/tags/create/${this.folderId}`, {
        name: this.name,
        colour: this.colour,
      })
      this.$fetch()
    },
  },
}
</script>
